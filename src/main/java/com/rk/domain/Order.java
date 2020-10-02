package com.rk.domain;

import java.time.LocalDateTime;
import java.util.Map;

public class Order {
    private long id;
    private User user;
    private String status;
    private Map<Glasses,Integer> glassesMap;
    private LocalDateTime orderTime;

}
