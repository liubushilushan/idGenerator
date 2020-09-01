package com.liuapi.identity.client;

import com.liuapi.identity.api.IdentityService;
import com.liuapi.identity.exception.SegmentExhaustedException;
import com.liuapi.identity.model.Segment;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
@Service
public class SimpleIdentityFactory implements IdentityFactory {
    @Reference
    private IdentityService identityService;

    private Map<String, Segment> currents = new ConcurrentHashMap<>();

    @Override
    public long generateId(String bizTag) {
        do {
            Segment segment = currents.get(bizTag);
            if (null == segment) {
                // 第一次加载号段
                currents.computeIfAbsent(bizTag, this::retrieveSegment);
            } else {
                // 后续加载号段
                try {
                    return segment.retrieve();
                } catch (SegmentExhaustedException e) {
                    // DCL
                    if (segment == currents.get(bizTag)){
                        synchronized (segment) {
                            /**
                             * 当且仅当已耗尽的号段存在在容器时才更新
                             */
                            if (segment == currents.get(bizTag)) {
                                currents.put(bizTag, this.retrieveSegment(bizTag));
                            }
                        }
                    }
                }
            }
        } while (true);
    }

    private Segment retrieveSegment(String bizTag){
        return identityService.retrieveSegment(bizTag);
    }
}
