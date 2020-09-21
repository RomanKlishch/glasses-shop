package com.rk.domain;

public enum UserRole {
    ADMIN("ADMIN"),USER("USER"),GUEST("GUEST");

    private String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
