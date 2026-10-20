package com.jane.janestore.service;


import com.jane.janestore.dto.OrderRequestDto;
import com.jane.janestore.dto.OrderResponseDto;
import com.jane.janestore.entity.Order;

import java.util.List;

public interface IOrderService {

    void createOrder(OrderRequestDto orderRequest);

    List<OrderResponseDto> getCustomerOrders();

    List<OrderResponseDto> getAllPendingOrders();

    void updateOrderStatus(Long orderId, String orderStatus);
}
