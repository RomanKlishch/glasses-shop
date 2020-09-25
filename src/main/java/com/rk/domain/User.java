package com.rk.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @EqualsAndHashCode.Exclude
    private LongId<User> id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
