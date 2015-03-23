package ru.cargoonline.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Server application runner/server application spring config.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
//@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ru.cargoonline.*"})
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}