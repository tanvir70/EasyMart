package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.Order;
import com.tanvir.easymart.domain.User;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository{

    public static final Set<Order> ORDERS = new CopyOnWriteArraySet<>();

    @Override
    public Order save(Order order) {
        ORDERS.add(order);
        return order;
    }

    @Override
    public Set<Order> findOrderByUser(User currentUser) {
        return ORDERS.stream()
                .filter(order -> order.getUser().equals(currentUser))
                .collect(Collectors.toSet());
    }
}
