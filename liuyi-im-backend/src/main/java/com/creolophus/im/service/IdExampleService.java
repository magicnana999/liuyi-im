package com.creolophus.im.service;

import com.creolophus.im.common.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/6/19 下午4:21
 */
@Service
public class IdExampleService extends BaseService {

    @Resource
    private GlobalAtomicGenerator atomicGenerator;

    @Resource
    private SnowFlakeGenerator snowFlakeGenerator;

    public Long nextGroupId(Long userId) {
        return atomicGenerator.nextId();
    }

    public Long nextUserId() {
        return snowFlakeGenerator.nextId();
    }
}
