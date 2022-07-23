package com.vinsguru.userservice.util;

import com.vinsguru.userservice.entity.User;
import com.vinsguru.userservice.entity.UserTransaction;
import com.vinsguru.userservice.grpc.dto.TransactionRequestDto;
import com.vinsguru.userservice.grpc.dto.TransactionResponseDto;
import com.vinsguru.userservice.grpc.dto.TransactionStatus;
import com.vinsguru.userservice.grpc.dto.UserDto;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoUtilProto {

    public static UserDto toDto(User user) {
        UserDto dto = UserDto.newBuilder().build();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static User toEntity(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDto requestDto) {
        UserTransaction ut = new UserTransaction();
        ut.setUserId(requestDto.getUserId());
        ut.setAmount(requestDto.getAmount());
        ut.setTransactionDate(LocalDateTime.now());
        return ut;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto requestDto, TransactionStatus status) {
        return TransactionResponseDto.newBuilder()
                .setAmount(requestDto.getAmount())
                .setUserId(requestDto.getUserId())
                .setStatus(status)
                .build();
    }


}
