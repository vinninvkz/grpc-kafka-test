package ru.vinninvkz.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {


    private final KafkaTemplate<String, Object> kafkaTemplate;

    private CompletableFuture<String> lastnameFuture = new CompletableFuture<>();

    private static final String TOPIC_LASTNAME = "${topic.lastname}";
    private static final String KAFKA_CONSUMER_GROUP_LASTNAME = "${spring.kafka.consumer.group-id}";

    private String lastname;


    @Value("${topic.id}")
    private String sendId;

    @KafkaListener(
            topics = TOPIC_LASTNAME,
            groupId = KAFKA_CONSUMER_GROUP_LASTNAME
    )
    public void listen(String message) {

        lastnameFuture.complete(message);
        lastnameFuture = new CompletableFuture<>();
        log.info("Received message: " + message);
    }

    public String getLastname() throws ExecutionException, InterruptedException, TimeoutException {
        return lastnameFuture.get(1, TimeUnit.SECONDS);
    }

    public void sendId(int id) {

        kafkaTemplate.send(sendId, id);

    }
}
