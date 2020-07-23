package com.creolophus.im.service;

import com.creolophus.im.storage.MessageIdStorage;
import com.creolophus.im.common.base.BaseService;
import com.creolophus.im.dao.SectionDao;
import com.creolophus.im.dao.SectionUserDao;
import com.creolophus.im.entity.MessageIdSection;
import com.creolophus.im.entity.MessageIdSectionUser;
import com.creolophus.im.vo.MessageIdSectionLimitVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author magicnana
 * @date 2019/10/21 下午5:12
 */
@Service
public class MessageIdService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MessageIdService.class);

    private static final int SECTION_CAPACITY = 10000;
//    private static final int SECTION_CAPACITY = 2;
    private static final Long SECTION_ID_OFFSET = 1000000000L;

    private static final int SECTION_STEP = 10000;
//    private static final int SECTION_STEP = 10;
    private static final Long MESSAGE_ID_OFFSET = 0L;
    private static final Long MESSAGE_ID_PEAKED = 9999999999999999L;


    private static final long batch =  SECTION_STEP>400?100:SECTION_STEP/4;

    @Resource
    private MessageIdStorage messageIdStorage;

    @Resource
    private SectionDao sectionDao;

    @Resource
    private SectionUserDao sectionUserDao;

    public void createSectionUserIfNotExist(Long userId) {
        MessageIdSectionUser bean = findSectionUserByUserId(userId);//这里可以不用写缓存，这个方法只在用户注册时调用1次。
        if(bean == null) {
            Long sectionId = getSectionId();
            insertSectionUser(sectionId, userId);
        }
    }

    private MessageIdSectionUser findSectionUserByUserId(Long userId) {
        MessageIdSectionUser messageIdSectionUser = sectionUserDao.createLambdaQuery().andEq(MessageIdSectionUser::getUserId, userId).single();
        return messageIdSectionUser;
    }

    public Long getSectionId() {
        Long currentSectionId = messageIdStorage.getSectionId();
        if(currentSectionId == null) {
            logger.debug("SectionId缓存,不存在");
            if(messageIdStorage.lockSetSectionId()) {
                try {
                    MessageIdSectionLimitVo sectionLimit = sectionDao.selectMaxSection();
                    if(sectionLimit == null) {
                        currentSectionId = SECTION_ID_OFFSET;
                        insertSectionWithNewSectionId(currentSectionId);
                    } else if(sectionLimit.getSize() == null || sectionLimit.getSize() < SECTION_CAPACITY) {
                        currentSectionId = sectionLimit.getSectionId();
                    } else {
                        currentSectionId = sectionLimit.getSectionId() + 1;
                        insertSectionWithNewSectionId(currentSectionId);
                    }
                    logger.debug("section_id_offset {},section_capacity {},new_section_id {}", SECTION_ID_OFFSET, SECTION_CAPACITY, currentSectionId);
                    messageIdStorage.setSectionId(currentSectionId);
                    return currentSectionId;
                } finally {
                    messageIdStorage.unlockSetSectionId();
                }
            } else {
                logger.debug("try again");
                return getSectionId();
            }
        } else {
            logger.debug("SectionId缓存,已存在 {}", currentSectionId);
            Integer size = sectionUserDao.selectSectionSize(currentSectionId);
            if(size != null && size >= SECTION_CAPACITY) {
                if(messageIdStorage.lockSetSectionId()) {
                    try {
                        currentSectionId = messageIdStorage.incrSectionId();
                        insertSectionWithNewSectionId(currentSectionId);
                        return currentSectionId;
                    } finally {
                        messageIdStorage.unlockSetSectionId();
                    }
                } else {
                    logger.debug("try again");
                    return getSectionId();
                }
            } else {
                return currentSectionId;
            }
        }
    }

    private Long getSectionIdByUserId(Long userId) {
        Long sectionId = messageIdStorage.getSectionIdByUserId(userId);
        if(sectionId == null) {
            if(messageIdStorage.lockSetSectionUser(userId)) {
                try {
                    MessageIdSectionUser messageIdSectionUser = findSectionUserByUserId(userId);
                    if(messageIdSectionUser == null) {
                        Long sectionIdNew = getSectionId();
                        messageIdSectionUser = insertSectionUser(sectionIdNew, userId);
                        //如果用户第一次发消息，那么没有SectionUser，需要在DB中创建一个
                    }
                    messageIdStorage.setSectionUser(userId, messageIdSectionUser.getSectionId());
                    return messageIdSectionUser.getSectionId();

                } finally {
                    messageIdStorage.unlockSetSectionUser(userId);
                }
            } else {
                return getSectionIdByUserId(userId);
            }
        } else {
            return sectionId;
        }
    }

    private MessageIdSection insertSectionWithNewSectionId(Long sectionId) {

        MessageIdSection messageIdSection = new MessageIdSection();
        messageIdSection.setCreateTime(new Date());
        messageIdSection.setCurrentId(MESSAGE_ID_OFFSET);
        messageIdSection.setMaxId(messageIdSection.getCurrentId() + SECTION_STEP);
        messageIdSection.setStep(SECTION_STEP);
        messageIdSection.setSectionId(sectionId);
        messageIdSection.setUpdateTime(messageIdSection.getCreateTime());
        sectionDao.insert(messageIdSection);
        return messageIdSection;
    }

    private MessageIdSectionUser insertSectionUser(Long sectionId, Long userId) {
        MessageIdSectionUser messageIdSectionUser = new MessageIdSectionUser();
        messageIdSectionUser.setSectionId(sectionId);
        messageIdSectionUser.setUserId(userId);
        sectionUserDao.insert(messageIdSectionUser);
        return messageIdSectionUser;
    }

    private Long getCurrentBySectionId(Long sectionId){
        return messageIdStorage.getCurrentFieldBySectionId(sectionId);
    }

    private Long getMaxBySectionId(Long sectionId){
        return messageIdStorage.getMaxFieldBySectionId(sectionId);
    }

    private Long makeSureSectionAllowAfterIncr(Long sectionId, long batch) {

        if(batch>SECTION_STEP){
            throw new RuntimeException("batch不能大于"+SECTION_STEP);
        }

        makeSureSectionInRedis(sectionId);
        Long currentId = messageIdStorage.incrCurrentId(sectionId, batch);
        MessageIdSection messageIdSection = messageIdStorage.getSection(sectionId);
        if(currentId > messageIdSection.getMaxId()) {
            messageIdStorage.delSection(sectionId);
            return makeSureSectionAllowAfterIncr(sectionId,batch);
        }
        return currentId;
    }

    private Long makeSureSectionInRedis(Long sectionId) {
        Long currentId = messageIdStorage.getCurrentFieldBySectionId(sectionId);
        if(currentId == null) {
            if(messageIdStorage.lockSetSection(sectionId)) {
                try {
                    MessageIdSection messageIdSection = selectSectionBySectionId(sectionId);
                    if(messageIdSection == null) {
                        throw new RuntimeException("DB中没有 message_id_session数据,这是不正常的.sectionId 生成后,应该原子性的生成 message_id_session数据 才对");
                    } else {
                        Long originMaxId = messageIdSection.getMaxId();
                        messageIdSection.setCurrentId(messageIdSection.getMaxId());
                        messageIdSection.setMaxId(messageIdSection.getMaxId()+ messageIdSection.getStep());
                        updateSectionCurrentAndMax(sectionId, messageIdSection.getCurrentId(), originMaxId);
                        messageIdStorage.setSectionHash(messageIdSection);
                        return messageIdSection.getCurrentId();
                    }
                } finally {
                    messageIdStorage.unlockSetSection(sectionId);
                }
            }else{
                return makeSureSectionInRedis(sectionId);
            }
        }else{
            return currentId;
        }
    }

    public Long nextId(Long userId) {
        Long sectionId = getSectionIdByUserId(userId);
        return makeSureSectionAllowAfterIncr(sectionId, 1);
    }

    private MessageIdSection selectSectionBySectionId(Long sectionId) {
        return sectionDao.single(sectionId);
    }

    private void updateSectionCurrentAndMax(Long sectionId, Long currentId, Long originMaxId) {
        sectionDao.updateCurrentAndMax(currentId,sectionId,originMaxId);
    }

}
