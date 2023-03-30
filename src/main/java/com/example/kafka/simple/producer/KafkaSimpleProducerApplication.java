package com.example.kafka.simple.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class KafkaSimpleProducerApplication implements DisposableBean {

    private static ConfigurableApplicationContext context;

    private static final int sleepInterval = 50;

    public static void main(String[] args) {
        context = SpringApplication.run(KafkaSimpleProducerApplication.class, args);
        SimpleMessageProducer producer = context.getBean(SimpleMessageProducer.class);

        byte[] buffer = new byte[1024];
        Arrays.fill(buffer, (byte) '0');

        String message = new String(buffer);
        try {
            while (true) {
                producer.sendMessageAsync(message);
                Thread.sleep(sleepInterval);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() throws Exception {
        log.info("Shutting down KafkaSimpleProducerApplication");
        context.close();
    }
}

