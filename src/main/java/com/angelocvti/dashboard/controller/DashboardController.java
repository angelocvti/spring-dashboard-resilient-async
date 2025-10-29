package com.angelocvti.dashboard.controller;

import com.angelocvti.dashboard.dto.UserDashboardDTO;
import com.angelocvti.dashboard.service.DashboardService;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@Validated
public class DashboardController {

  private final DashboardService dashboardService;

  public DashboardController(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @GetMapping("/{userId}")
  public UserDashboardDTO getDashboard(@PathVariable @Min(1) Long userId) {
    return dashboardService.getDashboard(userId);
  }
}
