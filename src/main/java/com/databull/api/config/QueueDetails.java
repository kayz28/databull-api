package com.databull.api.config;

import lombok.Data;

@Data
public class QueueDetails {
        private String queueName;
        private String exchangeName;
        private String exchangeType;
        private String routingKey;
}

