package com.msgsystems.jbugger.echipa2.backend.model;

public class RolePermissionPairDTO {
    private final String roleType;
    private final String permissionType;

    public RolePermissionPairDTO(String roleType, String permissionType) {
        this.roleType = roleType;
        this.permissionType = permissionType;
    }

    public String getRoleType() {
        return roleType;
    }

    public String getPermissionType() {
        return permissionType;
    }
}
