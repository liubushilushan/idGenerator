package com.liuapi.identity.api;

import com.liuapi.identity.model.Segment;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
public interface IdentityService {
    Segment retrieveSegment(String bizTag);
}
