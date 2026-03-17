package com.hertz.hertzreservation.client;

import com.hertz.hertzreservation.dto.BirthdayEventDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
 * Client responsible for calling kafka-event-service
 */
@Service
public class KafkaEventClient {

    private static final Logger logger =
            LoggerFactory.getLogger(KafkaEventClient.class);

    private final RestTemplate restTemplate;

    // URL of kafka-event-service
    private static final String KAFKA_EVENT_URL =
            "http://kafka-event-service:8082/events/birthday";

    public KafkaEventClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void publishBirthdayEvent(BirthdayEventDTO event) {

        try {

            logger.info("Calling kafka-event-service for email={}", event.getEmail()); // log API call

            restTemplate.postForEntity(KAFKA_EVENT_URL, event, String.class); // call kafka-event-service

            logger.info("Birthday event sent to kafka-event-service");

        } catch (Exception ex) {

            // Kafka failure must NOT break customer creation
            logger.error("Failed to publish birthday event", ex);

        }
    }
}