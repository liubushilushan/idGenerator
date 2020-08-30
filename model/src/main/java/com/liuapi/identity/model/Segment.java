package com.liuapi.identity.model;

import com.liuapi.identity.exception.SegmentExhaustedException;
import lombok.Data;

import java.io.Serializable;

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

    private long min;
    private long max;
    private long next;
    public Segment(long min,long max){
        this.min = min;
        this.max = max;
        this.next = min;
    }
    public synchronized long retrieve() throws SegmentExhaustedException {
        if(next<=max){
            return next++;
        }
        throw new SegmentExhaustedException();
    }

    @Override
    public String toString() {
        return "Segment{" +
                "min=" + min +
                ", max=" + max +
                ", next=" + next +
                '}';
    }
}
