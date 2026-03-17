package com.hertz.hertzreservation.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Custom Mongo configuration.
 * 
 * Instead of hardcoding localhost, we read the connection
 * string from application.properties.
 * 
 * This allows Docker and local environments to work correctly.
 */
@Configuration
public class MongoConfig {

    // Reads spring.data.mongodb.uri from application.properties
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
        // Create client using externalized configuration
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        // Database name is already inside URI,
        // but we keep it explicit for clarity
        return new MongoTemplate(mongoClient(), "hertz_db");
    }
}