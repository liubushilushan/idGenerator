package com.liuapi.identity.client.test.service;

import com.liuapi.identity.client.impl.SegmentChainIdentityFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrganizationService {
    public static final String BIZ_TAG = "organization";
    @Autowired
    private SegmentChainIdentityFactory segmentChainIdentityFactory;

    public long createOrganization() {
        long orgId = segmentChainIdentityFactory.generateId(BIZ_TAG);
        log.info("create organization. orgId is {}", orgId);
        return orgId;
    }



}
