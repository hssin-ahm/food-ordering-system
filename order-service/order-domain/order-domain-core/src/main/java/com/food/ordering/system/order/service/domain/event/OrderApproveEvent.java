package com.food.ordering.system.order.service.domain.event;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderApproveEvent extends OrderEvent{
    private final DomainEventPublisher<OrderApproveEvent> orderApproveEventDomainEventPublisher;

    public OrderApproveEvent(Order order, ZonedDateTime createdAt, DomainEventPublisher<OrderApproveEvent> orderApproveEventDomainEventPublisher) {
        super(order, createdAt);
        this.orderApproveEventDomainEventPublisher = orderApproveEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderApproveEventDomainEventPublisher.publish(this);

    }
}
