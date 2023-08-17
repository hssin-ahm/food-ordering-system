package com.food.ordering.system.order.service.messaging.mapper;

import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import com.food.ordering.system.kafka.order.avro.model.RestaurantOrderStatus;
import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.service.domain.event.OrderApproveEvent;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public RestaurantApprovalResponse
    approvalResponseAvroModelToApprovalResponse(RestaurantApprovalResponseAvroModel
                                                        restaurantApprovalResponseAvroModel) {
        return RestaurantApprovalResponse.builder()
                .id(restaurantApprovalResponseAvroModel.getId())
                .sagaId(restaurantApprovalResponseAvroModel.getSagaId())
                .restaurantId(restaurantApprovalResponseAvroModel.getRestaurantId())
                .orderId(restaurantApprovalResponseAvroModel.getOrderId())
                .createdAt(restaurantApprovalResponseAvroModel.getCreatedAt())
                .orderApprovalStatus(com.food.ordering.system.domain.valueobject.OrderApprovalStatus.valueOf(
                        restaurantApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                .failureMessages(restaurantApprovalResponseAvroModel.getFailureMessages())
                .build();
    }

    public RestaurantApprovalRequestAvroModel
    orderApproveEventToRestaurantApprovalRequestAvroModel(OrderApproveEvent orderApproveEvent) {
        Order order = orderApproveEvent.getOrder();
        return RestaurantApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(order.getId().getValue().toString())
                .setRestaurantId(order.getRestaurantId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setRestaurantOrderStatus(com.food.ordering.system.kafka.order.avro.model.RestaurantOrderStatus
                        .valueOf(order.getOrderStatus().name()))
                .setProducts(order.getItems().stream().map(orderItem ->
                        com.food.ordering.system.kafka.order.avro.model.Product.newBuilder()
                                .setId(orderItem.getProduct().getId().getValue().toString())
                                .setQuantity(orderItem.getQuantity())
                                .build()).collect(Collectors.toList()))
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderApproveEvent.getCreatedAt().toInstant())
                .setRestaurantOrderStatus(RestaurantOrderStatus.PENDING)
                .build();
    }

    public RestaurantApprovalRequestAvroModel orderCreatedEventToRestaurantApprovalRequestAvroModel(OrderCreatedEvent domainEvent) {

        Order order = domainEvent.getOrder();
        return RestaurantApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setOrderId(order.getId().getValue().toString())
                .setRestaurantId(order.getRestaurantId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setRestaurantOrderStatus(com.food.ordering.system.kafka.order.avro.model.RestaurantOrderStatus
                        .valueOf(order.getOrderStatus().name()))
                .setProducts(order.getItems().stream().map(orderItem ->
                        com.food.ordering.system.kafka.order.avro.model.Product.newBuilder()
                                .setId(orderItem.getProduct().getId().getValue().toString())
                                .setQuantity(orderItem.getQuantity())
                                .build()).collect(Collectors.toList()))
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(domainEvent.getCreatedAt().toInstant())
                .setRestaurantOrderStatus(RestaurantOrderStatus.PENDING)
                .build();
    }

    public RestaurantApprovalRequestAvroModel orderCancelledEventToRestaurantApprovalRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {
        Order order = orderCancelledEvent.getOrder();
        return RestaurantApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
                .setRestaurantOrderStatus(RestaurantOrderStatus.CANCELLED)
                .build();
    }
}
