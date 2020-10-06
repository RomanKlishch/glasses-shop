package com.rk.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private long id;
    private User user;
    private String status;
    private LocalDateTime orderTime;
    @EqualsAndHashCode.Exclude
    private Map<Glasses, Integer> glassesMap;

}
