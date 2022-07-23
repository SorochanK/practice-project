package com.vinsguru.userservice.service;

import com.vinsguru.userservice.grpc.dto.ReactorTransactionServiceGrpc;
import com.vinsguru.userservice.grpc.dto.TransactionRequestDto;
import com.vinsguru.userservice.grpc.dto.TransactionResponseDto;
import com.vinsguru.userservice.grpc.dto.TransactionStatus;
import com.vinsguru.userservice.repository.UserRepository;
import com.vinsguru.userservice.repository.UserTransactionRepository;
import com.vinsguru.userservice.util.EntityDtoUtil;
import com.vinsguru.userservice.util.EntityDtoUtilProto;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@GrpcService
public class GrpcTransactionService extends ReactorTransactionServiceGrpc.TransactionServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;

    @Override
    public Mono<TransactionResponseDto> createTransaction(Mono<TransactionRequestDto> request) {
        return request.flatMap(requestDto ->
                this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                        .filter(Boolean::booleanValue)
                        .map(b -> EntityDtoUtilProto.toEntity(requestDto))
                        .flatMap(this.transactionRepository::save)
                        .map(ut -> EntityDtoUtilProto.toDto(requestDto, TransactionStatus.APPROVED))
                        .defaultIfEmpty(EntityDtoUtilProto.toDto(requestDto, TransactionStatus.DECLINED)));
    }
}
