package com.vinsguru.orderservice.util;

import com.vinsguru.orderservice.dto.OrderStatus;
import com.vinsguru.orderservice.dto.PurchaseOrderResponseDto;
import com.vinsguru.orderservice.dto.RequestContextProto;
import com.vinsguru.productservice.grpc.dto.ProductDto;
import com.vinsguru.userservice.grpc.dto.TransactionResponseDto;
import com.vinsguru.userservice.grpc.dto.TransactionRequestDto;
import com.vinsguru.orderservice.entity.PurchaseOrder;
import com.vinsguru.userservice.grpc.dto.TransactionStatus;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtilProto {

    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder){
        PurchaseOrderResponseDto dto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder, dto);
        dto.setOrderId(purchaseOrder.getId());
        return dto;
    }

    public static void setTransactionRequestDto(RequestContextProto requestContextProto){
        TransactionRequestDto transactionRequestDto = TransactionRequestDto.newBuilder()
                .setUserId(requestContextProto.getPurchaseOrderRequestDto().getUserId())
                .setAmount(requestContextProto.getProductDto().getPrice()).build();
        requestContextProto.setTransactionRequestDto(transactionRequestDto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContextProto requestContextProto){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContextProto.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(requestContextProto.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(requestContextProto.getProductDto().getPrice());
        TransactionStatus status = requestContextProto.getTransactionResponseDto().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        purchaseOrder.setStatus(orderStatus);
        return purchaseOrder;
    }


}
