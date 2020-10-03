package com.rk.domain;

public enum UserRole {
    ADMIN("ADMIN"), USER("USER"), GUEST("GUEST");

    private String nameOfUserRole;

    UserRole(String nameOfUserRole) {
        this.nameOfUserRole = nameOfUserRole;
    }

    public String getNameOfUserRole() {
        return nameOfUserRole;
    }
}
