package ru.vinninvkz.aggregator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vinninvkz.aggregator.service.HelloServiceGrpcImpl;
import ru.vinninvkz.aggregator.service.KafkaService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class AggregatorController {


    private final KafkaService kafkaService;

    private final HelloServiceGrpcImpl helloService;

    @GetMapping("/grpc")
    public String getName(@RequestParam int id) {

        String name = helloService.getName(id);
        log.info("Received name from grpc-client: " + name);
        return name;
    }

    @GetMapping("/kafka")
    public String getLastname(@RequestParam int id) throws ExecutionException, InterruptedException, TimeoutException {

        kafkaService.sendId(id);
        log.info("Send to kafka-client id: " + id);

        String lastname = kafkaService.getLastname();
        log.info("Received from kafka-client lastname: " + lastname);

        return lastname;
    }

    @GetMapping
    public String getUsverData(@RequestParam int id) {

        try {
            String name = getName(id);
            String lastname = getLastname(id);

            String data =
                    "Name : " +
                            name +
                            "\n" +
                            "Lastname : " +
                            lastname;


            return data;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return exception.getMessage();
        }

    }



}
