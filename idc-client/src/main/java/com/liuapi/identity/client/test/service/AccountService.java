package com.liuapi.identity.client.test.service;

import com.liuapi.identity.client.impl.SegmentChainIdentityFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountService {
    public static final String BIZ_TAG = "user";
    @Autowired
    private SegmentChainIdentityFactory segmentChainIdentityFactory;

    public void createUser(String name) {
        long orgId = segmentChainIdentityFactory.generateId(BIZ_TAG);
        log.info("create user. uid is {}", orgId);
    }
}
