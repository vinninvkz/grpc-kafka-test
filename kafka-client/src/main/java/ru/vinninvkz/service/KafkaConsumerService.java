package ru.vinninvkz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.vinninvkz.dto.UsverDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final LastnameService lastnameService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_ID = "${topic.id}";
    private static final String KAFKA_CONSUMER_GROUP_ID = "${spring.kafka.consumer.group-id}";


    @Value("${topic.lastname}")
    private String sendClientTopic;

    @KafkaListener(
            topics = TOPIC_ID,
            groupId = KAFKA_CONSUMER_GROUP_ID,
            properties = "spring.json.value.default.type"
    )
    public void consume(Integer message) {

        int id = message;
        log.info("Consume id: " + id);
        try {
            UsverDto usver = lastnameService.getUsver(id);
            String lastname = usver.getLastname();
            log.info("Received from db lastname: " + lastname);
            sendLastnameEvent(lastname);

        } catch (Exception exception) {
            log.error("Usver with id: " + id + " not found (from kafka-client)");
            sendLastnameEvent("Usver with id: " + id + " not found (from kafka-client)");
        }


    }


    public void sendLastnameEvent(String lastname) {

        kafkaTemplate.send(sendClientTopic, lastname);
        log.info("Send lastname: " + lastname);
    }


}
