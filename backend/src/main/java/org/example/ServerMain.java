package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.TimeZone;

@MapperScan("org.example.mapper")
@SpringBootApplication
public class ServerMain {

    public static void main(String[] args) {
        SpringApplication.run(ServerMain.class, args);
    }

    @PostConstruct
    void started() {
        // 设置用户时区为 UTC
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));
    }
}
