package com.liuapi.identity.client.test.service;

import com.liuapi.identity.client.impl.SegmentChainIdentityFactory;
import com.liuapi.identity.client.test.mapper.TestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    public static final String BIZ_TAG = "user";
    public static final String ROLE_TAG = "role";
    public static final String APP_TAG = "app";
    @Autowired
    private SegmentChainIdentityFactory segmentChainIdentityFactory;
    @Autowired
    private TestMapper testMapper;

    /**
     * 1） 测试功能1
     * 验证主键不重复
     * 调用接口获取id并插入到表中，如果表有报主键冲突，则测试失败
     */
    public void testCreateStrategy1() {
        long orgId = segmentChainIdentityFactory.generateId(BIZ_TAG);
        // insert into user table
        testMapper.insert(orgId,Thread.currentThread().getId(),BIZ_TAG);
    }
    /**
     * 2） 测试功能2
     * 调用接口获取id
     * 如果拿到id之后插入到表中，如果表有有报主键冲突，则测试失败
     */
    public void testCreateStrategy2() {
        long orgId =  segmentChainIdentityFactory.generateId(ROLE_TAG);
        // insert into user table
        testMapper.insert(orgId,Thread.currentThread().getId(),ROLE_TAG);
    }

    public long testTps1() {
        long orgId = segmentChainIdentityFactory.generateId(BIZ_TAG);
        return orgId;
    }
    public long testTps2() {
        long orgId = segmentChainIdentityFactory.generateId(ROLE_TAG);
        return orgId;
    }
    public long testTps3() {
        long orgId = segmentChainIdentityFactory.generateId(APP_TAG);
        return orgId;
    }

}
