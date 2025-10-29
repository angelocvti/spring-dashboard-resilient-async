package com.angelocvti.dashboard.dto;

import com.angelocvti.dashboard.model.Order;
import com.angelocvti.dashboard.model.User;
import java.util.List;

public class UserDashboardDTO {

    private User user;
    private List<Order> orders;
    private long reviewCount;

    public UserDashboardDTO(User user, List<Order> orders, long reviewCount) {
        this.user = user;
        this.orders = orders;
        this.reviewCount = reviewCount;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public List<Order> getOrders() { return orders; }

    public void setOrders(List<Order> orders) { this.orders = orders; }

    public long getReviewCount() { return reviewCount; }

    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
}
