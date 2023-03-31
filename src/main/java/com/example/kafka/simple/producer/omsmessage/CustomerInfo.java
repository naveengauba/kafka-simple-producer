package com.example.kafka.simple.producer.omsmessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerInfo {

    private String id;

    private String firstname;

    private String lastname;

}
