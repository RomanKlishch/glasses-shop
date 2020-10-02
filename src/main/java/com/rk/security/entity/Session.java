package com.rk.security.entity;

import com.rk.domain.Glasses;
import com.rk.domain.Order;
import com.rk.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {
    private String token;
    private User user;
    private LocalDateTime expireDate;
    private Order order;
}
