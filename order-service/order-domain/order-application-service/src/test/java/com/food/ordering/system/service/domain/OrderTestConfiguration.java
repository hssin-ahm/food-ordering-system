package com.food.ordering.system.service.domain;

import com.food.ordering.system.order.service.domain.OrderDomainService;
import com.food.ordering.system.order.service.domain.OrderDomainServiceImpl;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.OrderCancelledRestaurantApproveRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.OrderCreatedRestaurantApproveRequestMessagePublisher;
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.food.ordering.system")
public class OrderTestConfiguration {

    @Bean
    public OrderCreatedRestaurantApproveRequestMessagePublisher orderCreatedRestaurantApproveRequestMessagePublisher(){
        return Mockito.mock(OrderCreatedRestaurantApproveRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledRestaurantApproveRequestMessagePublisher orderCancelledRestaurantApproveRequestMessagePublisher(){
        return Mockito.mock(OrderCancelledRestaurantApproveRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
