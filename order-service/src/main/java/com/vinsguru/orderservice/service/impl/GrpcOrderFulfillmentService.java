package com.vinsguru.orderservice.service.impl;

import com.vinsguru.orderservice.dto.PurchaseOrderRequestDto;
import com.vinsguru.orderservice.dto.PurchaseOrderResponseDto;
import com.vinsguru.orderservice.dto.RequestContextProto;
import com.vinsguru.orderservice.repository.PurchaseOrderRepository;
import com.vinsguru.orderservice.util.EntityDtoUtilProto;
import com.vinsguru.productservice.grpc.dto.Id;
import com.vinsguru.productservice.grpc.dto.ReactorProductServiceGrpc;
import com.vinsguru.userservice.grpc.dto.ReactorTransactionServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class GrpcOrderFulfillmentService {

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @GrpcClient("product-service")
    private ReactorProductServiceGrpc.ReactorProductServiceStub productStub;

    @GrpcClient("user-service")
    private ReactorTransactionServiceGrpc.ReactorTransactionServiceStub transactionStub;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return requestDtoMono.map(RequestContextProto::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtilProto::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtilProto::getPurchaseOrder)
                .map(this.orderRepository::save) // blocking
                .map(EntityDtoUtilProto::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContextProto> productRequestResponse(RequestContextProto rc) {
        String productId = rc.getPurchaseOrderRequestDto().getProductId();
        return productStub.getProductById(Mono.just(Id.newBuilder().setId(productId).build()))
                .doOnNext(rc::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }

    private Mono<RequestContextProto> userRequestResponse(RequestContextProto rc) {
        return transactionStub.createTransaction(Mono.just(rc.getTransactionRequestDto()))
                .doOnNext(rc::setTransactionResponseDto)
                .thenReturn(rc);
    }
}
