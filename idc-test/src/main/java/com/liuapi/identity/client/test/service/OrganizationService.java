package com.liuapi.identity.client.test.service;

import com.liuapi.identity.client.SegmentChainIdentityFactory;
import com.liuapi.identity.client.test.IdentityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    @Autowired
    private SegmentChainIdentityFactory segmentChainIdentityFactory;

}
