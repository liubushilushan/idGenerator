package com.liuapi.identity.client.test.controller;

import com.liuapi.identity.client.test.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/9/1
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private OrganizationService organizationService;
    /**
     * 创建机构
     */
    private ExecutorService workers = Executors.newFixedThreadPool(20);
    @GetMapping("/create/org/{number}")
    public List<Long> createOrganizations(@PathVariable("number") int number){
        List<Long> ids = new ArrayList<>(number);
        while(number-->0) {
            long id = organizationService.createOrganization();
            ids.add(id);
        }
        Collections.sort(ids);
        return ids;
    }
}
