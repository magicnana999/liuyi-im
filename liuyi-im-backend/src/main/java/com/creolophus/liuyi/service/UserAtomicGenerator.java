package com.creolophus.liuyi.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author magicnana
 * @date 2020/6/19 下午4:23
 */
@Service
public class UserAtomicGenerator {

    private static final ConcurrentHashMap<Long,AtomicLong> table = new ConcurrentHashMap<>();

    public Long next(Long userId){
        AtomicLong al = table.get(userId);
        if(al == null){
            synchronized (String.valueOf(userId).intern()){
                al = new AtomicLong(100);
                table.put(userId, al);
            }
        }
        return al.getAndIncrement();
    }

}
