package com.msgsystems.jbugger.echipa2.backend.model;

public class UserRolePairDTO {
    private final String username;
    private final String roleType;

    public UserRolePairDTO(String username, String roleType) {
        this.username = username;
        this.roleType = roleType;
    }

    public String getUsername() {
        return username;
    }

    public String getRoleType() {
        return roleType;
    }
}
