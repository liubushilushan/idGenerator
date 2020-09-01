package com.liuapi.identity;

import com.liuapi.identity.model.Segment;
import com.liuapi.identity.service.SegmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
@Slf4j
@SpringBootApplication
public class IdentityServer {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(IdentityServer.class);
        SegmentService service = context.getBean(SegmentService.class);
        Segment segment = service.retrieveSegment("reload.times");
        log.info("Server Reload Times is {}",segment.getMin());
    }
}
