package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.Order;

public interface OrderRepository {
    Order save(Order order);
}
