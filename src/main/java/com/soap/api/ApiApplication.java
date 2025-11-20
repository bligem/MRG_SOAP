package com.soap.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class ApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ApiApplication.class, args);
        String mode = ctx.getEnvironment().getProperty("spring.app.profiles.active");
        log.info("Application running in {} mode", mode);
	}
}
