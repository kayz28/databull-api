package com.databull.api.service;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.UUID;
import java.util.Map;

public class KafkaProducerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private Map<String , Object> kafkaConfigMap;
    private final KafkaProducer<String, Object> kafkaProducer;

    public KafkaProducerService(Map<String, Object> config) {
        kafkaConfigMap = config;
        kafkaProducer = new KafkaProducer<>(config);
    }

    public KafkaProducerService(Properties properties) {
        kafkaProducer = new KafkaProducer<>(properties);
    }

    //async
    public void send(String topicName, Object payload) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topicName, uuidAsString, payload);
        log.debug("Started to push data into Kafka");
        this.kafkaProducer.send(producerRecord, (recordMetadata, exception) -> {
            if (exception == null) {
                log.info("Received new metadata. \n" +
                        "Topic:" + recordMetadata.topic() + "\n" +
                        "Partition: " + recordMetadata.partition() + "\n" +
                        "Offset: " + recordMetadata.offset() + "\n" +
                        "Timestamp: " + recordMetadata.timestamp());
            } else {
                log.error("Error while producing", exception);
            }
        } );

        log.debug("Finished to push data into Kafka");
    }

    //async
    public void sendBulkPayload(String topicName, Map<String, Object> payload) throws Exception {
        log.debug("Starting pushing logs to Kafka.....");
        try {
            for(Map.Entry<String, Object> entry : payload.entrySet()) {
                this.send(topicName, entry.getValue());
            }
        } catch (Exception e) {
            throw new Exception();
        }
        log.debug("Finished pushing logs to Kafka!.");
    }

    public void close() {
        kafkaProducer.flush();
        kafkaProducer.close();
    }

}
