package com.tanvir.easymart.service;

import com.tanvir.easymart.domain.Order;
import com.tanvir.easymart.domain.ShippingAddress;
import com.tanvir.easymart.domain.User;
import com.tanvir.easymart.dto.ShippingAddressDTO;
import com.tanvir.easymart.exceptions.CartItemNotFoundException;
import com.tanvir.easymart.repository.CartRepository;
import com.tanvir.easymart.repository.OrderRepository;
import com.tanvir.easymart.repository.ShippingAddressRepository;

public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ShippingAddressRepository shippingAddressRepository;
    private CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ShippingAddressRepository shippingAddressRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void processOrder(ShippingAddressDTO shippingAddressDTO, User currentUser) {
        var shippingAddress = convertTo(shippingAddressDTO);
        var savedShippingAddress = shippingAddressRepository.save(shippingAddress);
        var cart = cartRepository.findByUser(currentUser)
                .orElseThrow(() ->
                        new CartItemNotFoundException("Cart Not found by current user"));
        Order order = new Order();
        order.setCart(cart);
        order.setShippingAddress(savedShippingAddress);
        order.setShipped(false);
        order.setUser(currentUser);
        orderRepository.save(order);
    }

    private ShippingAddress convertTo(ShippingAddressDTO shippingAddressDTO) {
        var shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(shippingAddressDTO.getAddress());
        shippingAddress.setAddress2(shippingAddressDTO.getAddress2());
        shippingAddress.setCountry(shippingAddressDTO.getCountry());
        shippingAddress.setState(shippingAddressDTO.getState());
        shippingAddress.setZip(shippingAddressDTO.getZip());
        shippingAddress.setMobileNumber(shippingAddressDTO.getMobileNumber());
        return shippingAddress;
    }
}