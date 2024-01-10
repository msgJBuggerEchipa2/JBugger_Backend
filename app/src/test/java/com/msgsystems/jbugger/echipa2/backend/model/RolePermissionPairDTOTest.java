package com.msgsystems.jbugger.echipa2.backend.model;

import com.msgsystems.jbugger.echipa2.backend.model.RolePermissionPairDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RolePermissionPairDTOTest {

    @Test
    public void testRolePermissionPairDTO() {
        // Create instance of RolePermissionPairDTO
        RolePermissionPairDTO rolePermissionPairDTO = new RolePermissionPairDTO("roleType", "permissionType");

        // Test the getRoleType method
        String roleType = rolePermissionPairDTO.getRoleType();
        assertEquals("roleType", roleType);

        // Test the getPermissionType method
        String permissionType = rolePermissionPairDTO.getPermissionType();
        assertEquals("permissionType", permissionType);
    }
}