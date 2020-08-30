package com.liuapi.service.identity.model;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/29
 */
public class Segment {
    private long min;
    private long max;
    private long next;
    public Segment(long min,long max){
        this.min = min;
        this.max = max;
        this.next = min;
    }
    public synchronized long retrieve() throws IdentityExhaustedException{
        if(next<=max){
            return next++;
        }
        throw new IdentityExhaustedException();
    }
}
