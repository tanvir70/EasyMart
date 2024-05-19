package com.tanvir.easymart.service;

import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.dto.ShippingAddressDTO;

public interface OrderService {
    void processOrder(ShippingAddressDTO shippingAddress, User CurrentUser);
}
