package com.alexlab.pi.deviceintegrationapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class DeviceIntegrationApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeviceIntegrationApiApplication.class, args);
    }
}
