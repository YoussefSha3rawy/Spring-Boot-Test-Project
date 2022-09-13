package com.eventum.backend;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public DozerBeanMapper createMapper(){return new DozerBeanMapper();}
}
