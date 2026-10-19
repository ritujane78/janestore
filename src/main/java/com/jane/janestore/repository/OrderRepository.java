package com.jane.janestore.repository;


import com.jane.janestore.entity.Customer;
import com.jane.janestore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

  /**
   * Fetch orders for a customer, sorted by creation date in descending order.
   */
  List<Order> findByCustomerOrderByCreatedAtDesc(Customer customer);

  List<Order> findByOrderStatus(String orderStatus);
}
