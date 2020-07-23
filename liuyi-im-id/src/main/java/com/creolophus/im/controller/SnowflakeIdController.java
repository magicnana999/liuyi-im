package com.creolophus.im.controller;

import com.creolophus.im.common.base.BaseController;
import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.id.MachineInfo;
import com.creolophus.liuyi.common.id.OriginalSnowflakeID;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author magicnana
 * @date 2019/6/4 上午12:17
 */

@Validated
@RestController
@RequestMapping(value = "/liuyi/id/snowflake_id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SnowflakeIdController extends BaseController {

    private static final ConcurrentHashMap<Long/**workerID**/, OriginalSnowflakeID> snowflakeIdTable = new ConcurrentHashMap<>();


    @RequestMapping(value = "/next_id", method = RequestMethod.POST)
    public ApiResult nextId() {

        long dcId = (MachineInfo.getMachineIdentifier() + MachineInfo.getProcessIdentifier() + "").hashCode() & OriginalSnowflakeID.getMaxDatacenterNum();
        long workerId = Thread.currentThread().getId() & OriginalSnowflakeID.getMaxMachineNum();

        OriginalSnowflakeID id = snowflakeIdTable.get(workerId);
        if(id == null) {
            id = new OriginalSnowflakeID(dcId,workerId,1568086869644L);
            snowflakeIdTable.put(Thread.currentThread().getId(), id);
        }
        return new ApiResult(id.nextId());
    }

}
