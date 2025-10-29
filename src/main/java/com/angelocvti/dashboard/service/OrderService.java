package com.angelocvti.dashboard.service;

import com.angelocvti.dashboard.model.Order;
import com.angelocvti.dashboard.repository.OrderRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository repository;

  public OrderService(OrderRepository repository) {
    this.repository = repository;
  }

  public List<Order> getRecentForUser(Long userId, int limit) {
    simulateDelay();

    Pageable pageable = PageRequest.of(0, limit);

    return repository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
  }

  private void simulateDelay() {
    try {
      Thread.sleep(500);
    } catch (InterruptedException ignored) {
    }
  }
}
