package com.liuapi.identity.service;

import com.liuapi.identity.api.IdentityService;
import com.liuapi.identity.mapper.IdentityMapper;
import com.liuapi.identity.model.Segment;
import com.liuapi.identity.model.domain.IdentityDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
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
     * @param biz_tag
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Segment retrieveSegment(String biz_tag){
        // 此处获取数据库行锁
        identityMapper.update(biz_tag);
        IdentityDO idc = identityMapper.query(biz_tag);

        long step = idc.getStep();
        long maxId = idc.getMaxId();

        return new Segment(maxId-step+1,maxId);
        // 释放行锁
    }



}
