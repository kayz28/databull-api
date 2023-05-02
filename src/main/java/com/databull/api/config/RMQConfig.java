//package com.databull.api.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
//import org.springframework.amqp.support.converter.MarshallingMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.amqp.core.Queue;
//
//@Configuration
//public class RMQConfig {
//
//    @Value("${rabbitmq.queue.name}")
//    private String queue;
//
//    @Value("${rabbitmq.dlq.queue.name}")
//    private String dlqQueue;
//
//    @Value("${rabbitmq.exchange.name}")
//    private String exchange;
//
//    @Value("${rabbitmq.dead.letter.exchange.name}")
//    private String dlqExchange;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;
//
//    @Value("${rabbitmq.dlq.routing.key}")
//    private String dlqRoutingKey;
//
//    @Bean
//    Queue primaryQueue() {
//        return QueueBuilder.durable(queue).build();
//    }
//
//    @Bean
//    Queue dlq() {
//        return QueueBuilder.durable(dlqQueue).build();
//    }
//
//    @Bean
//    DirectExchange exchange() {
//        return new DirectExchange(exchange);
//    }
//
//    @Bean
//    DirectExchange dlqExchange() {
//        return new DirectExchange(dlqExchange);
//    }
//
//    @Bean
//    Binding binding() {
//        return BindingBuilder.bind(primaryQueue()).to(exchange()).with(routingKey);
//    }
//
//    @Bean
//    Binding dlqBinding() {
//        return BindingBuilder.bind(dlq()).to(dlqExchange()).with(dlqRoutingKey);
//    }
//
//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        return container;
//    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public MessageConverter xmlMessageConverter() {
//        return new Jackson2XmlMessageConverter();
//    }
//
//    @Bean
//    public MessageConverter xmlMarshallMessageConverter() {
//        return new MarshallingMessageConverter();
//    }
//
//    public String getQueue() {
//        return queue;
//    }
//
//    public void setQueue(String queue) {
//        this.queue = queue;
//    }
//
//    public String getDlqQueue() {
//        return dlqQueue;
//    }
//
//    public void setDlqQueue(String dlqQueue) {
//        this.dlqQueue = dlqQueue;
//    }
//
//    public String getExchange() {
//        return exchange;
//    }
//
//    public void setExchange(String exchange) {
//        this.exchange = exchange;
//    }
//
//    public String getDlqExchange() {
//        return dlqExchange;
//    }
//
//    public void setDlqExchange(String dlqExchange) {
//        this.dlqExchange = dlqExchange;
//    }
//
//    public String getRoutingKey() {
//        return routingKey;
//    }
//
//    public void setRoutingKey(String routingKey) {
//        this.routingKey = routingKey;
//    }
//
//    public String getDlqRoutingKey() {
//        return dlqRoutingKey;
//    }
//
//    public void setDlqRoutingKey(String dlqRoutingKey) {
//        this.dlqRoutingKey = dlqRoutingKey;
//    }
//
//}
