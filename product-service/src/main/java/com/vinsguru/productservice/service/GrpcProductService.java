package com.vinsguru.productservice.service;


import com.vinsguru.productservice.grpc.dto.Id;
import com.vinsguru.productservice.grpc.dto.ProductDto;
import com.vinsguru.productservice.grpc.dto.ReactorProductServiceGrpc;
import com.vinsguru.productservice.repository.ProductRepository;
import com.vinsguru.productservice.util.EntityDtoUtilProto;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@GrpcService
public class GrpcProductService extends ReactorProductServiceGrpc.ProductServiceImplBase {

    @Autowired
    private ProductRepository repository;

    @Override
    public Mono<ProductDto> getProductById(Mono<Id> request) {
        return request.flatMap(id -> repository.findById(id.getId()))
                .map(EntityDtoUtilProto::toDto);
    }
}
