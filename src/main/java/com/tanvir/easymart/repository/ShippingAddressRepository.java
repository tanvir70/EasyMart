package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.ShippingAddress;

public interface ShippingAddressRepository {
    ShippingAddress save(ShippingAddress shippingAddress);
}
