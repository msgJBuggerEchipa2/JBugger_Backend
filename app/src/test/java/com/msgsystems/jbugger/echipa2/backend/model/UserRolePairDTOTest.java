package com.msgsystems.jbugger.echipa2.backend.model;

import com.msgsystems.jbugger.echipa2.backend.model.UserRolePairDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRolePairDTOTest {

    @Test
    public void testUserRolePairDTO() {
        // Create instance of UserRolePairDTO
        UserRolePairDTO userRolePairDTO = new UserRolePairDTO("roleType", "permissionType");

        // Test the getPermissionType method
        String username = userRolePairDTO.getUsername();
        assertEquals("username", username);

        // Test the getRoleType method
        String roleType = userRolePairDTO.getRoleType();
        assertEquals("roleType", roleType);
    }
}