package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ExecutionException;

public class Producer {
    public static void main(String[] args) {
        produce("kafka-topic-1");
    }
    public static void produce(String topicName) {
        try (org.apache.kafka.clients.producer.Producer<Void, String> producer = new KafkaProducer<>(CreateTopic.clientProperties())) {
            for (int i = 0; i < 100; i++) {
                ProducerRecord<Void, String> record = new ProducerRecord<>(topicName, String.format("sending asynchronous message no #%d to producer where topic name is #%s", i,topicName));
                producer.send(record, (r, e) -> {
                    if (e == null) {
                        System.out.printf("Sent to topic '%s' at offset %d\n", r.topic(), r.offset());
                    } else {
                        System.out.println("Error sending message: " + e);
                    }
                });
            }
        }
    }
}