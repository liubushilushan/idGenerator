package com.liuapi.identity.service;

import com.liuapi.identity.mapper.IdentityMapper;
import com.liuapi.identity.model.Segment;
import com.liuapi.identity.model.domain.IdentityDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 号段服务
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
@Service
public class SegmentService {
    @Resource
    private IdentityMapper identityMapper;

    /**
     * 获取新的号段
     * @param bizTag
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Segment retrieveSegment(String bizTag) {
        // 此处获取数据库行锁
        int update = identityMapper.update(bizTag);
        if(update == 0){
            /**
             * 场景：没有这个业务标签
             * * RC的隔离级别下多个线程可能会同时执行identityMapper.insertOrUpdateMaxId这个操作。
             * * RR的隔离级别下只有一个线程会执行identityMapper.insertOrUpdateMaxId这个操作，
             * 其他线程阻塞在identityMapper.update操作上。
             */
            identityMapper.insertOrUpdateMaxId(bizTag);
        }
        IdentityDO idc = identityMapper.query(bizTag);

        long step = idc.getStep();
        long maxId = idc.getMaxId();

        return new Segment(maxId-step+1,maxId,bizTag);
        // 释放行锁
    }



}
