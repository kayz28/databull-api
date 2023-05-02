package com.databull.api.service;

import com.databull.api.config.QueueDetails;
import com.databull.api.config.RMQRefinedConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MarshallingMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitMQGenericProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQGenericProducer.class);

    @Autowired
    private RMQRefinedConfig rmqRefinedConfig;

    private String dataType;

    //serializer
    private static final String JSON = "json";
    private static final String XML = "xml";
    private static final String XML_MARSHALL= "xml_marshall";

    private static final RabbitTemplate rabbitTemplate = new RabbitTemplate();

    public RabbitMQGenericProducer() {
    }

    public RabbitMQGenericProducer(String dataForm) {
        this.dataType = dataForm;
        init();
    }
    private QueueDetails getQueueDetails(String queueName) throws Exception{
        Map<String, QueueDetails> rmqCrud = rmqRefinedConfig.getRmqCrudDetails();
//        Map<String, QueueDetails> rmqWorker = rmqRefinedConfig.getRmqWorkerDetails();

        if(rmqCrud.containsKey(queueName))
            return rmqCrud.get(queueName);

//        if(rmqWorker.containsKey(queueName))
//            return rmqWorker.get(queueName);

        else throw new Exception("No such queue present.");
    }
    private void init() {
        switch (dataType) {
            case JSON -> rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            case XML -> rabbitTemplate.setMessageConverter(new Jackson2XmlMessageConverter());
            case XML_MARSHALL -> rabbitTemplate.setMessageConverter(new MarshallingMessageConverter());
            default -> throw new IllegalArgumentException("No data form present for serializing and deserializing.");
        }
    }

    public <T> void sendMessage(String queueType, T message) throws Exception {
        try {
            LOGGER.info("Sending message to Rmq.");
            QueueDetails queueDetails = getQueueDetails(queueType);
            rabbitTemplate.convertAndSend(queueDetails.getExchangeName(), queueDetails.getRoutingKey(), message.toString());
            LOGGER.info("Message sent.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public <T> void sendAsyncMessage(String queueType, T message) throws Exception {

    }

}