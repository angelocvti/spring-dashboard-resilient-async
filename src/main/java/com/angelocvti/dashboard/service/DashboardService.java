package com.angelocvti.dashboard.service;

import com.angelocvti.dashboard.dto.UserDashboardDTO;
import com.angelocvti.dashboard.model.Order;
import com.angelocvti.dashboard.model.User;
import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  private static final Logger log = LoggerFactory.getLogger(DashboardService.class);

  private final UserService userService;
  private final OrderService orderService;
  private final ReviewService reviewService;

  private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

  public DashboardService(
      UserService userService, OrderService orderService, ReviewService reviewService) {
    this.userService = userService;
    this.orderService = orderService;
    this.reviewService = reviewService;
  }

  public UserDashboardDTO getDashboardExampleOne(Long userId) {
    log.info("Fetching dashboard for user {}", userId);

    try {
      User user = userService.getById(userId);
      List<Order> orders = orderService.getRecentForUser(userId, 10);
      Long reviews = reviewService.countByUser(userId);

      return new UserDashboardDTO(user, orders, reviews);
    } catch (Exception ex) {
      log.error("Failed to build dashboard for user {}: {}", userId, ex.getMessage());

      throw new RuntimeException("Error building dashboard", ex);
    }
  }

  public UserDashboardDTO getDashboardExampleTwo(Long userId) {
    log.info("Fetching dashboard for user {}", userId);

    CompletableFuture<User> user =
        CompletableFuture.supplyAsync(() -> userService.getById(userId));

    CompletableFuture<List<Order>> orders =
        CompletableFuture.supplyAsync(() -> orderService.getRecentForUser(userId, 10));

    CompletableFuture<Long> reviews =
        CompletableFuture.supplyAsync(() -> reviewService.countByUser(userId));

    try {
      return new UserDashboardDTO(user.get(), orders.get(), reviews.get());
    } catch (Exception ex) {
      log.error("Failed to build dashboard for user {}: {}", userId, ex.getMessage());

      throw new RuntimeException("Error building dashboard", ex);
    }
  }

  public UserDashboardDTO getDashboard(Long userId) {
    log.info("Fetching dashboard for user {}", userId);

    CompletableFuture<User> users =
        CompletableFuture.supplyAsync(() -> userService.getById(userId), executor)
            .orTimeout(2, TimeUnit.SECONDS)
            .exceptionally(
                ex -> {
                  log.warn("Failed to fetch user {}: {}", userId, ex.getMessage());

                  return User.guestUser();
                });

    CompletableFuture<List<Order>> orders =
        CompletableFuture.supplyAsync(() -> orderService.getRecentForUser(userId, 10), executor)
            .orTimeout(3, TimeUnit.SECONDS)
            .exceptionally(
                ex -> {
                  log.warn("Failed to fetch orders for user {}: {}", userId, ex.getMessage());

                  return List.of();
                });

    CompletableFuture<Long> reviews =
        CompletableFuture.supplyAsync(() -> reviewService.countByUser(userId), executor)
            .orTimeout(2, TimeUnit.SECONDS)
            .exceptionally(
                ex -> {
                  log.warn("Failed to fetch reviews for user {}: {}", userId, ex.getMessage());

                  return 0L;
                });

    return CompletableFuture.allOf(users, orders, reviews)
        .thenApply(
            v -> {
              log.info("Successfully built dashboard for user {}", userId);

              return new UserDashboardDTO(users.join(), orders.join(), reviews.join());
            })
        .join();
  }

  @PreDestroy
  public void shutdown() {
    log.info("Shutting down custom executor...");

    executor.shutdown();
  }
}
