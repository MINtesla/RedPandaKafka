package org.example;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

public class Consumer {
    public static void main(String[] args) {
        consume("kafka-topic-1");
    }

    public static void consume(String topicName) {
        Properties props = CreateTopic.clientProperties();
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        try (org.apache.kafka.clients.consumer.Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            Set<TopicPartition> topicPartitions = Collections.singleton(new TopicPartition(topicName, 0));
            consumer.assign(topicPartitions);
            consumer.seekToBeginning(topicPartitions);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
                if (records.count() == 0) break;
                for (ConsumerRecord<String, String> receivedRecord : records) {
                    System.out.printf("topic: %s (%d|%d), key: %s, %s\n",
                            receivedRecord.topic(), receivedRecord.partition(), receivedRecord.offset(),
                            receivedRecord.key(), receivedRecord.value());
                }
                System.out.println("record list end");
            }
        }
    }
}