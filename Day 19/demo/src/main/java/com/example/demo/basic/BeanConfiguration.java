package com.example.demo.basic;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public BasicBean basicBean() {
        return new BasicBean("Bhumiiiii");
    }
}
