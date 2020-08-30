package com.liuapi.identity.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
@Data
public class IdentityDO {
    private String bizTag;
    private long maxId;
    private long step;
    private Date updateTime;
}
