package com.lab.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("entities")
public class PersistenceConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
