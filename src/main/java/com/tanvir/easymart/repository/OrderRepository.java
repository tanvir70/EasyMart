package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.Order;
import com.tanvir.easymart.domain.User;

import java.util.Set;

public interface OrderRepository {
    Order save(Order order);

    Set<Order> findOrderByUser(User user);
}
