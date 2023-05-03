//package com.databull.api.config;
//
//import jakarta.annotation.PostConstruct;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
//import org.springframework.amqp.support.converter.MarshallingMessageConverter;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//
//import java.util.*;
//
//
//@Configuration
//@Component
//public class RMQRefinedConfig {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RMQRefinedConfig.class);
//
//    //crud constants
//    private static final String CREATE_Q = "create";
//    private static final String UPDATE_Q = "update";
//    private static final String DELETE_Q = "delete";
//
//    //worker constants
//    private static final String worker1 = "worker1";
//
//    //exchange constants
//    private static final String TOPIC_EXCHANGE = "topicExchange";
//    private static final String DIRECT_EXCHANGE = "directExchange";
//    private static final String FANOUT_EXCHANGE = "fanoutExchange";
//    private static final String HEADERS_EXCHANGE = "headersExchange";
//
//    @Value(value = "#{${rabbitmq.crud.queues.details}}")
//    private Map<String, Map<String, String>> rmqCrudDetails;
//
//    private Map<String, QueueDetails> rmqCrudQueueDetails;
//
//    @Value(value = "#{${rabbitmq.worker.queues.details}}")
//    private Map<String, Map<String, String>> rmqWorkerDetails;
//
//    private Map<String, QueueDetails> rmqWorkerQueueDetails;
//
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private BeanFactory beanFactory;
//
//    public QueueDetails getCreateQDetails() {
//        return rmqCrudQueueDetails.get(CREATE_Q);
//    }
//
//    public QueueDetails getUpdateQDetails() {
//        return rmqCrudQueueDetails.get(UPDATE_Q);
//    }
//
//    public QueueDetails getDeleteQDetails() {
//        return rmqCrudQueueDetails.get(DELETE_Q);
//    }
//
//    public Map<String, Map<String, String>> getRmqCrudDetails() {
//        return rmqCrudDetails;
//    }
////
////    public void setRmqCrudDetails(Map<String, QueueDetails> rmqCrudDetails) {
////        this.rmqCrudDetails = rmqCrudDetails;
////    }
////
//    public Map<String, QueueDetails> getRmqWorkerDetails() {
//        return rmqWorkerQueueDetails;
//    }
////
////    public void setRmqWorkerDetails(Map<String, QueueDetails> rmqWorkerDetails) {
////        this.rmqWorkerDetails = rmqWorkerDetails;
////    }
//
//    @PostConstruct
//    private void init() throws Exception {
//        Map<String, String> mp = rmqCrudDetails.get("createq");
//        QueueDetails queueDetails = new QueueDetails();
//
//        queueDetails.setQueueName(mp.get("queueName"));
//        queueDetails.setExchangeName(mp.get("exchangeName"));
//        queueDetails.setExchangeType(mp.get("exchangeType"));
//        queueDetails.setRoutingKey(mp.get("routingKey"));
//        rmqCrudQueueDetails = new HashMap<>();
//        rmqCrudQueueDetails.put("createq", queueDetails);
//        System.out.println(rmqCrudQueueDetails);
//        configure(rmqCrudQueueDetails);
//
//
////        Map<String, String> mp1 = rmqWorkerDetails.get("worker1");
////        QueueDetails queueDetails1 = new QueueDetails();
////
////        queueDetails.setQueueName(mp1.get("queueName"));
////        queueDetails.setExchangeName(mp1.get("exchangeName"));
////        queueDetails.setExchangeType(mp1.get("exchangeType"));
////        queueDetails.setRoutingKey(mp1.get("routingKey"));
////        rmqWorkerQueueDetails = new HashMap<>();
////        rmqWorkerQueueDetails.put("worker1", queueDetails);
////        configure(rmqWorkerQueueDetails);
////        configure(rmqWorkerDetails);
//
//    }
//
//    private void configure(Map<String, QueueDetails> rmqDetails) throws Exception {
//        if (rmqDetails.entrySet().size() == 0) throw new Exception("No queue details present");
//
//        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
//        for (Map.Entry<String, QueueDetails> queueDetails : rmqDetails.entrySet()) {
//            QueueDetails queueDetails1 = queueDetails.getValue();
//            configureQueues(configurableBeanFactory, queueDetails1, false);
//            configureQueues(configurableBeanFactory, queueDetails1, true);
//        }
//    }
//
//    private void configureQueues(ConfigurableBeanFactory configurableBeanFactory, QueueDetails queueDetails, boolean isDlq)
//            throws Exception {
//
//        String queueName = isDlq ? "dlq_" + queueDetails.getQueueName() : queueDetails.getQueueName();
//        String exchangeName = isDlq ? "dlq_" + queueDetails.getExchangeName() : queueDetails.getExchangeName();
//        String exchangeType = isDlq ? "dlq_" + queueDetails.getExchangeType() : queueDetails.getExchangeType();
//        String routingKey = isDlq ? "dlq_" + queueDetails.getRoutingKey() : queueDetails.getRoutingKey();
//        Queue qObj = new Queue(queueName);
//        configurableBeanFactory.registerSingleton(queueName, qObj);
//        Exchange exchange = getExchange(exchangeName, exchangeType);
//        configurableBeanFactory.registerSingleton(exchangeName, exchange);
//        configurableBeanFactory.registerSingleton(queueName + "_binding", BindingBuilder.bind(qObj)
//                .to(exchange)
//                .with(routingKey));
//    }
//
//
//    private Exchange getExchange(String exchangeName, String exchangeType) throws Exception {
//        if (Objects.isNull(exchangeName) || Objects.isNull(exchangeType))
//            throw new NullPointerException("Please check exchange name and exchange type, either of them are null");
//        if (Objects.equals(exchangeName, exchangeType))
//            throw new Exception("ExchangeName and exchangeType can not be same");
//
//        switch (exchangeType) {
//            case TOPIC_EXCHANGE -> {
//                return new TopicExchange(exchangeName);
//            }
//            case FANOUT_EXCHANGE -> {
//                return new FanoutExchange(exchangeName);
//            }
//            case HEADERS_EXCHANGE -> {
//                return new HeadersExchange(exchangeName);
//            }
//            default -> {
//                return new DirectExchange(exchangeName);
//            }
//        }
//    }
//
//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
//        return container;
//    }
//
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        return connectionFactory;
//    }
//
//    private static final String JSON = "json";
//    private static final String XML = "xml";
//    private static final String XML_MARSHALL= "xml_marshall";
//     private void setMessageConverters(String dataType) {
//        switch (dataType) {
//             case JSON -> rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//             case XML -> rabbitTemplate.setMessageConverter(new Jackson2XmlMessageConverter());
//             case XML_MARSHALL -> rabbitTemplate.setMessageConverter(new MarshallingMessageConverter());
//             default -> throw new IllegalArgumentException("No data form present for serializing and deserializing.");
//         }
//     }
//
//}
