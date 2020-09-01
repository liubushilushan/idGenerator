package com.liuapi.identity.client.impl;

import com.liuapi.identity.api.IdentityService;
import com.liuapi.identity.client.IdentityFactory;
import com.liuapi.identity.exception.SegmentExhaustedException;
import com.liuapi.identity.model.Segment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
@Service
public class SegmentChainIdentityFactory implements IdentityFactory {
    @Autowired
    private IdentityService identityService;

    private Map<String, Segment> currents = new ConcurrentHashMap<>();
    private ExecutorService idWorkers = new ThreadPoolExecutor(
            3, 3, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>()
    );


    @Override
    public long generateId(String bizTag) {
        do {
            Segment segment = currents.get(bizTag);
            if (segment == null) {
                currents.computeIfAbsent(bizTag, this::retrieveSegment);
            } else {
                try {
                    long allocatedId = segment.retrieve();
                    if (segment.getSignalId() == allocatedId) {
                        // 提交加载任务给线程池
                        idWorkers.submit(segment.getNextTask());
                    }
                    return allocatedId;
                } catch (SegmentExhaustedException e) {
                    if (segment == currents.get(bizTag)) {
                        synchronized (segment) {
                            if (segment == currents.get(bizTag)) {
                                FutureTask<Segment> nextTask = segment.getNextTask();
                                try {
                                    Segment nextSegment = nextTask.get(10, TimeUnit.SECONDS);
                                    currents.put(bizTag, nextSegment);
                                } catch (InterruptedException | ExecutionException | TimeoutException e1) {
                                    e1.printStackTrace();
                                    // 再次执行该任务
                                    nextTask.run();
                                }
                            }
                        }
                    }
                }
            }
        } while (true);
    }

    private Segment retrieveSegment(String bizTag) {
        Segment nextSegment = identityService.retrieveSegment(bizTag);
        nextSegment.setNextTask(new FutureTask<>(() -> this.retrieveSegment(bizTag)));
        return nextSegment;
    }
}
