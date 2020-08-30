package com.liuapi.identity.client;

import com.liuapi.identity.api.IdentityService;
import com.liuapi.identity.exception.SegmentExhaustedException;
import com.liuapi.identity.model.Segment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
public class SingleSegmentIdentityPool implements IdentityPool {
    private IdentityService identityService;

    private Map<String, Segment> currents = new ConcurrentHashMap<>();

    @Override
    public long generateId(String bizTag) {
        do {
            Segment segment = currents.get(bizTag);
            if (segment != null) {
                try {
                    return segment.retrieve();
                } catch (SegmentExhaustedException e) {
                    // 表示当前号段的id已用尽
                }
            }
            // ConcurrentHashMap确保只有一个线程会去加载新的号段
            currents.computeIfAbsent(bizTag, identityService::retrieveSegment);
        } while (true);
    }
}
