package com.rk.domain;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private long id;
    private User user;
    private String status;
    @EqualsAndHashCode.Exclude
    private Map<Glasses, Integer> glassesMap;

}
