package com.liuapi.identity.provider;

import com.liuapi.identity.api.IdentityService;
import com.liuapi.identity.model.Segment;
import com.liuapi.identity.service.SegmentService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
@Service(retries = 2,interfaceClass = IdentityService.class)
public class IdentityServiceProvider implements IdentityService {
    @Autowired
    private SegmentService segmentService;

    public Segment retrieveSegment(String bizTag)  {
        return segmentService.retrieveSegment(bizTag);
    }
}
