package com.example.kafka.simple.producer;

import com.example.kafka.simple.producer.omsmessage.CustomerInfo;
import com.example.kafka.simple.producer.omsmessage.ItemInfo;
import com.example.kafka.simple.producer.omsmessage.OmsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Service
@Slf4j
public class ProducerService {

    private final int sleepInterval = 1000;

    @Value("${produce.dummy.payload}")
    private boolean produceDummyPayload;

    @Value("${produce.oms.payload}")
    private boolean produceOmsPayload;

    private final SimpleMessageProducer simpleProducer;

    private final SimpleJsonMessageProducer jsonMessageProducer;

    public ProducerService(SimpleMessageProducer producer, SimpleJsonMessageProducer jsonMessageProducer) {
        this.simpleProducer = producer;
        this.jsonMessageProducer = jsonMessageProducer;
    }

    public void doProduceMessages() {
        if (produceDummyPayload) {
            byte[] smallByteBuffer = new byte[1024];
            Arrays.fill(smallByteBuffer, (byte) '0');
            String message = new String(smallByteBuffer);
            try {
                while (true) {
                    simpleProducer.sendMessageAsync(message);
                    Thread.sleep(sleepInterval);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("Producing dummy messages is disabled..");
        }

        if (produceOmsPayload) {
            byte[] largeByteBuffer = new byte[100 * 1024];
            Arrays.fill(largeByteBuffer, (byte) '0');
            OmsMessage omsMessage = mockOmsMessage(largeByteBuffer);
            try {
                while (true) {
                    jsonMessageProducer.sendMessageAsync(omsMessage);
                    Thread.sleep(sleepInterval);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("Producing OMS messages is disabled..");
        }
    }

    private static OmsMessage mockOmsMessage(byte[] largeByteBuffer) {
        String orderId = UUID.randomUUID().toString();
        String custId = UUID.randomUUID().toString();
        String itemId = UUID.randomUUID().toString();
        return OmsMessage.builder().orderId(orderId).
                customerInfo(CustomerInfo.builder().id(custId).firstname("fName").lastname("lName").build())
                .items(Collections.singletonList(ItemInfo.builder().id(itemId).desc("An item desc").quantity(1).build()))
                .dummyPayload(largeByteBuffer).build();
    }

}
