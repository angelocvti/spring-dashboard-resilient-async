package com.angelocvti.dashboard.repository;

import com.angelocvti.dashboard.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  long countByUserId(Long userId);
}
