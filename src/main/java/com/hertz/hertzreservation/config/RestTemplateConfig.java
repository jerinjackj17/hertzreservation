package com.hertz.hertzreservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*
 * HTTP client configuration
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate(); // used to call external services
    }
}