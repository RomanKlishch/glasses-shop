package com.rk.domain;

public enum UserRole {
    ADMIN("ADMIN"),USER("USER"),GUEST("GUEST");

    private String nameRole;

    UserRole(String userRole) {
        this.nameRole = userRole;
    }

    public String getNameRole() {
        return nameRole;
    }
}
