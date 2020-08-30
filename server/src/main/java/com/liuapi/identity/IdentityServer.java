package com.liuapi.identity;

import com.liuapi.identity.model.Segment;
import com.liuapi.identity.service.SegmentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
@SpringBootApplication
public class IdentityServer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(IdentityServer.class);
        SegmentService service = context.getBean(SegmentService.class);

        Segment segment = service.retrieveSegment("user");
        System.out.println(segment);
    }
}
