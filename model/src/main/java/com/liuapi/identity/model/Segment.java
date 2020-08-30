package com.liuapi.identity.model;

import com.liuapi.identity.exception.SegmentExhaustedException;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/29
 */
@Data
public class Segment implements Serializable {
    public static final long serialVersionUID = 1L;

    private final String bizTag;
    private final long min;
    private final long max;
    // 最大的已分配id
    private long nextId;
    // 标记位进行通知
    private long signalId;
    private FutureTask<Segment> next;

    public Segment(long min,long max,String bizTag){
        if(min>max){
            throw new IllegalArgumentException("minId is larger than maxId");
        }
        this.min = min;
        this.max = max;
        this.nextId = min;
        this.bizTag = bizTag;
        long step = max-min+1;
        this.signalId = (long)Math.floor(step*0.1)+min;
    }

    public synchronized long retrieve() throws SegmentExhaustedException {
        if(nextId <=max){
            return nextId++;
        }
        throw new SegmentExhaustedException();
    }

    @Override
    public String toString() {
        return "Segment{" +
                "bizTag='" + bizTag + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", nextId=" + nextId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return min == segment.min &&
                max == segment.max &&
                Objects.equals(bizTag, segment.bizTag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bizTag, min, max);
    }
}
