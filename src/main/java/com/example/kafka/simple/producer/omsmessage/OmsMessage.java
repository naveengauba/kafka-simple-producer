package com.example.kafka.simple.producer.omsmessage;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OmsMessage {

    private String orderId;

    private CustomerInfo customerInfo;

    private List<ItemInfo> items;

    private byte[] dummyPayload;

}
