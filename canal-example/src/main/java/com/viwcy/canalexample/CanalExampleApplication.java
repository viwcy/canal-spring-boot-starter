package com.viwcy.canalexample;

import com.viwcy.canalspringbootstarter.event.EnableCanal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.viwcy.**"})
@EnableCanal
public class CanalExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalExampleApplication.class, args);
    }

}
