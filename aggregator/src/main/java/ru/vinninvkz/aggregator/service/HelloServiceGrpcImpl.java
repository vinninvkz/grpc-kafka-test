package ru.vinninvkz.aggregator.service;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.vinninvkz.HelloServiceGrpc;
import ru.vinninvkz.HelloServiceOuterClass;

@Service
@Slf4j
public class HelloServiceGrpcImpl {

    @GrpcClient("hello-service")
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;


    public String getName(int id){
        HelloServiceOuterClass.HelloRequest request = HelloServiceOuterClass.HelloRequest.newBuilder().setId(id).build();
        log.info("Create request to grpc-client: " + request);
        HelloServiceOuterClass.HelloResponse response = helloServiceBlockingStub.greeting(request);
        log.info("Received response from grpc-client: " + response);
        return response.getName();
    }

}
