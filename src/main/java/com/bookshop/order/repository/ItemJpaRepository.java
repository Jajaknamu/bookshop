package com.bookshop.order.repository;

import com.bookshop.order.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {
}
