package com.example.kafka.simple.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class KafkaSimpleProducerApplication implements DisposableBean {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(KafkaSimpleProducerApplication.class, args);
        ProducerService producerService = context.getBean(ProducerService.class);
        producerService.doProduceMessages();
    }

    @Override
    public void destroy() throws Exception {
        log.info("Shutting down KafkaSimpleProducerApplication");
        context.close();
    }

}

