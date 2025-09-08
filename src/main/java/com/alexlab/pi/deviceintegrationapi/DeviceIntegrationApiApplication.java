package com.alexlab.pi.deviceintegrationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@ComponentScan
public class DeviceIntegrationApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeviceIntegrationApiApplication.class, args);
    }
}
