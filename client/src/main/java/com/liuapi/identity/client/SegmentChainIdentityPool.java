package com.liuapi.identity.client;

import com.liuapi.identity.api.IdentityService;
import com.liuapi.identity.exception.SegmentExhaustedException;
import com.liuapi.identity.exception.SegmentLoadFailException;
import com.liuapi.identity.model.Segment;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 当前号段用尽，则使用下一个号段
 *
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
public class SegmentChainIdentityPool implements IdentityPool {
    private IdentityService identityService;
    private Map<String, Segment> currents = new ConcurrentHashMap<>();
    private ExecutorService idWorkers = new ThreadPoolExecutor(
            2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100)
    );


    @Override
    public long generateId(String bizTag) throws InterruptedException {
        do {
            Segment segment = currents.get(bizTag);
            if (segment != null) {
                try {
                    long allocatedId = segment.retrieve();
                    if (segment.getSignalId() == allocatedId) {
                        // 提交加载任务给线程池
                        idWorkers.submit(segment.getNextTask());
                    }
                    return allocatedId;
                } catch (SegmentExhaustedException e) {
                    // 表示当前号段的id已用尽,则获取下一个号段
                }
            }
            // ConcurrentHashMap确保只有一个线程会去加载新的号段
            try {
                currents.computeIfAbsent(bizTag, a -> {
                    if (segment == null) {
                        try {
                            return new SegmentLoadTask(bizTag).call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            return segment.getNextTask().get(100, TimeUnit.SECONDS);
                        } catch (InterruptedException | ExecutionException | TimeoutException e) {
                            e.printStackTrace();
                        }
                    }
                    throw new SegmentLoadFailException();
                });
            } catch (SegmentLoadFailException e) {
                throw e;
            }
        } while (true);
    }

    public class SegmentLoadTask implements Callable<Segment> {
        public final String bizTag;

        public SegmentLoadTask(String bizTag) {
            this.bizTag = bizTag;
        }

        @Override
        public Segment call() throws Exception {
            Segment next = identityService.retrieveSegment(bizTag);
            next.setNextTask(new FutureTask<>(new SegmentLoadTask(bizTag)));
            return next;
        }
    }
}
