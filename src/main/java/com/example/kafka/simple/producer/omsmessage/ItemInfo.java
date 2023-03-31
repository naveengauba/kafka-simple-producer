package com.example.kafka.simple.producer.omsmessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemInfo {

    private String id;

    private String desc;

    private int quantity;
}
