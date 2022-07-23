package com.vinsguru.orderservice.dto;

import com.vinsguru.productservice.grpc.dto.ProductDto;
import com.vinsguru.userservice.grpc.dto.TransactionResponseDto;
import com.vinsguru.userservice.grpc.dto.TransactionRequestDto;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class RequestContextProto {

    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContextProto(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }
}
