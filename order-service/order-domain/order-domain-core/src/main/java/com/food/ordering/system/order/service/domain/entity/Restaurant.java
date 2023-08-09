package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {
    private final List<Product> products;
    private boolean active;
}
