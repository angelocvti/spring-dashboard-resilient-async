package com.angelocvti.dashboard.repository;

import com.angelocvti.dashboard.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
