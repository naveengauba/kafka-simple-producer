package com.example.kafka.simple.producer;


import com.example.kafka.simple.producer.omsmessage.OmsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class SimpleJsonMessageProducer {

    private final String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private long messagePublished;

    public SimpleJsonMessageProducer(@Value(value = "${oms.topic.name}") String topicName, KafkaTemplate<String, Object> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(OmsMessage omsMessage) {
        kafkaTemplate.send(topicName, omsMessage);
    }

    public void sendMessageAsync(OmsMessage omsMessage) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, omsMessage);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                messagePublished++;
                log.info("Sent message with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send message=[" +
                        omsMessage + "] due to : " + ex.getMessage());
            }
        });
    }

}

