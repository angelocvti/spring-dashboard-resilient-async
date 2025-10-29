package com.angelocvti.dashboard.service;

import com.angelocvti.dashboard.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private final ReviewRepository repository;

  public ReviewService(ReviewRepository repository) {
    this.repository = repository;
  }

  public long countByUser(Long userId) {
    simulateDelay();

    return repository.countByUserId(userId);
  }

  private void simulateDelay() {
    try {
      Thread.sleep(400);
    } catch (InterruptedException ignored) {
    }
  }
}
