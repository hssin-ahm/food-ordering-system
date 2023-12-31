package com.food.ordering.system.order.service.messaging.publisher.kafka;

import com.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.food.ordering.system.kafka.producer.KafkaMessageHelper;
import com.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.OrderRestaurantRequestMessagePublisher;
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateOrderKafkaMessagePublisher implements OrderRestaurantRequestMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;

    private final KafkaMessageHelper orderKafkaMessageHelper;


    public CreateOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                            OrderServiceConfigData orderServiceConfigData,
                                            KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer, KafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderCreatedEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();
        log.info("Received OrderCreatedEvent for order id: {}", orderId);
        RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel =
                orderMessagingDataMapper.orderCreatedEventToRestaurantApprovalRequestAvroModel(domainEvent);

        try {
            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    orderId,
                    restaurantApprovalRequestAvroModel,
                    orderKafkaMessageHelper
                            .getKafkaCallback(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                                    restaurantApprovalRequestAvroModel,
                                    orderId,
                                    "RestaurantApprovalRequestAvroModel")
            );
            log.info("RestaurantApprovalRequestAvroModel sent to kafka for order id: {}", orderId);
        }catch (Exception e) {
            log.error("Error while sending RestaurantApprovalRequestAvroModel message" +
                    " to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}
