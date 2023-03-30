package com.example.kafka.simple.producer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class SimpleMessageProducer {

    private final String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private long messagePublished;

    public SimpleMessageProducer(@Value(value = "${spring.kafka.topicName}") String topicName, KafkaTemplate<String, String> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String msg) {
        kafkaTemplate.send(topicName, msg);
    }

    public void sendMessageAsync(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                messagePublished++;
                log.info("Sent message with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }

}

