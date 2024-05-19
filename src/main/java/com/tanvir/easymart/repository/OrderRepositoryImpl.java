package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.Order;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class OrderRepositoryImpl implements OrderRepository{

    public static final Set<Order> ORDERS = new CopyOnWriteArraySet<>();

    @Override
    public Order save(Order order) {
        ORDERS.add(order);
        return order;
    }
}
