package com.angelocvti.dashboard.repository;

import com.angelocvti.dashboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
