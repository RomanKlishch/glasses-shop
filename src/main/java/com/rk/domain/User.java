package com.rk.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private LongId<User> id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
