package com.jane.janestore.repository;


import com.jane.janestore.entity.Customer;
import com.jane.janestore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

  /**
   * Fetch orders for a customer, sorted by creation date in descending order.
   */
  List<Order> findByCustomerOrderByCreatedAtDesc(Customer customer);

  List<Order> findByOrderStatus(String orderStatus);

//  @Query("SELECT o FROM Order o where o.customer= :custommer ORDER BY o.createdAt DESC")
//  List<Order> findOrdersByCustomer(@Param("customer") Customer customer);
//
//  @Query("SELECT  o from Order o where o.orderStatus = ?1")
//  List<Order> findOrdersByStatus(String orderStatus);

  @Query(value = "SELECT * FROM orders o where o.customer_id= :customer_id ORDER BY o.created_at DESC", nativeQuery = true)
  List<Order> findOrdersByCustomerWithNativeQuery(@Param("customer_id") Long customer_id);

  @Query(value = "SELECT  * from orders o where o.order_status = ?1", nativeQuery = true)
  List<Order> findOrdersByStatusWithNativeQuery(String orderStatus);
}
