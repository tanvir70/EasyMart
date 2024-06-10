package com.tanvir.easymart.repository;

import com.tanvir.easymart.domain.ShippingAddress;

import java.util.Optional;

public interface ShippingAddressRepository {
    ShippingAddress save(ShippingAddress shippingAddress);
    Optional<ShippingAddress> findOne(Long shippingAddressId);
}
