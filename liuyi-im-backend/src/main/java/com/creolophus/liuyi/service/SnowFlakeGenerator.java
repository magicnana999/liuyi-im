package com.creolophus.liuyi.service;

import com.creolophus.liuyi.common.id.MachineInfo;
import com.creolophus.liuyi.common.id.OriginalSnowflakeID;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author magicnana
 * @date 2020/6/19 下午4:27
 */
@Service
public class SnowFlakeGenerator {

    private static final ConcurrentHashMap<Long/**workerID**/, OriginalSnowflakeID> snowflakeIdTable = new ConcurrentHashMap<>();

    public Long nextId() {

        long dcId = (MachineInfo.getMachineIdentifier() + MachineInfo.getProcessIdentifier() + "").hashCode() & OriginalSnowflakeID.getMaxDatacenterNum();
        long workerId = Thread.currentThread().getId() & OriginalSnowflakeID.getMaxMachineNum();

        OriginalSnowflakeID id = snowflakeIdTable.get(workerId);
        if(id == null) {
            id = new OriginalSnowflakeID(dcId,workerId,1568086869644L);
            snowflakeIdTable.put(Thread.currentThread().getId(), id);
        }
        return id.nextId();
    }
}
