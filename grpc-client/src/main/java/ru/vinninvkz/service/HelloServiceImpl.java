package ru.vinninvkz.service;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vinninvkz.HelloServiceGrpc;
import ru.vinninvkz.HelloServiceOuterClass;
import ru.vinninvkz.dto.UsverDto;

@GRpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private final NameService nameService;

    @Autowired
    public HelloServiceImpl(NameService nameService) {
        this.nameService = nameService;
    }

    @Override
    public void greeting(HelloServiceOuterClass.HelloRequest request,
                         StreamObserver<HelloServiceOuterClass.HelloResponse> responseObserver) {


        int id = request.getId();

        HelloServiceOuterClass.HelloResponse response;


        try {
            UsverDto usver = nameService.getUsver(id);
            response = HelloServiceOuterClass.
                    HelloResponse.newBuilder()
                    .setName(usver.getName())
                    .build();

        } catch (Exception e) {

            response = HelloServiceOuterClass.
                    HelloResponse.newBuilder()
                    .setName("Usver with id: " + id + " not found (from grpc-client)")
                    .build();
        }

        responseObserver.onNext(response);


        responseObserver.onCompleted();
    }
}
