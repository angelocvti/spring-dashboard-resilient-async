package com.angelocvti.dashboard.service;

import com.angelocvti.dashboard.model.User;
import com.angelocvti.dashboard.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public User getById(Long id) {
    simulateDelay();

    return repository.findById(id).orElse(User.guestUser());
  }

  private void simulateDelay() {
    try {
      Thread.sleep(300);
    } catch (InterruptedException ignored) {
    }
  }
}
