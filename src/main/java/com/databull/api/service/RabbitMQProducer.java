package com.databull.api.service;//package com.databull.api.service;
//
//import com.databull.api.config.QueueDetails;
//import com.databull.api.config.RMQRefinedConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
//import org.springframework.amqp.support.converter.MarshallingMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//public class RabbitMQGenericProducer {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQGenericProducer.class);
//
//    private String dataType;
//
//    @Autowired
//    RMQRefinedConfig rmqRefinedConfig;
//
//    //serializer
//    private static final String JSON = "json";
//    private static final String XML = "xml";
//    private static final String XML_MARSHALL= "xml_marshall";
//
//    @Bean
//    public static ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        return connectionFactory;
//    }
//
//    private static final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
//
//    public RabbitMQGenericProducer(String dataForm) {
//        this.dataType = dataForm;
//        init();
//    }
//    private QueueDetails getQueueDetails(String queueName) throws Exception {
//        Map<String, Map<String, String>> rmqCrud = rmqRefinedConfig.getRmqCrudDetails();
////        Map<String, QueueDetails> rmqWorker = rmqRefinedConfig.getRmqWorkerDetails();
//        System.out.println(rmqCrud);
//        Map<String, String> mp = rmqCrud.get("createq");
//        QueueDetails queueDetails = new QueueDetails();
//
//        queueDetails.setQueueName(mp.get("queueName"));
//        queueDetails.setExchangeName(mp.get("exchangeName"));
//        queueDetails.setExchangeType(mp.get("exchangeType"));
//        queueDetails.setRoutingKey(mp.get("routingKey"));
//        return queueDetails;
////        if(rmqWorker.containsKey(queueName))
////            return rmqWorker.get(queueName);
//
//
//    }
//    private void init() {
//        switch (dataType) {
//            case JSON -> rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//            case XML -> rabbitTemplate.setMessageConverter(new Jackson2XmlMessageConverter());
//            case XML_MARSHALL -> rabbitTemplate.setMessageConverter(new MarshallingMessageConverter());
//            default -> throw new IllegalArgumentException("No data form present for serializing and deserializing.");
//        }
//    }
//
//    public <T> void sendMessage(String queueType, T message) throws Exception {
//        try {
//            LOGGER.info("Sending message to Rmq.");
//            QueueDetails queueDetails = rmqRefinedConfig.getCreateQDetails();
//            rabbitTemplate.convertAndSend(queueDetails.getExchangeName(), queueDetails.getExchangeName(), message);
//            LOGGER.info("Message sent.");
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//    }
//
//}

//same result
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RabbitMQProducer {
//
//    @Value("${rabbitmq.exchange.name}")
//    private String exchange;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
//
//    private RabbitTemplate rabbitTemplate;
//
//    public RabbitMQProducer() {
//        this.rabbitTemplate = new RabbitTemplate(connectionFactory());
//        rabbitTemplate.setMessageConverter(converter());
//    }
//
//    @Bean
//    public MessageConverter converter(){
//        return new Jackson2JsonMessageConverter();
//    }
//
//    ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses("localhost");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        return connectionFactory;
//    }
//
//    public <T> void sendMessage(T message){
//        LOGGER.info(String.format("Message sent -> %s", message));
//        rabbitTemplate.convertAndSend(exchange, routingKey, message.toString());
//    }
//}