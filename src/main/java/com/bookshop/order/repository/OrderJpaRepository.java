package com.bookshop.order.repository;

import com.bookshop.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
