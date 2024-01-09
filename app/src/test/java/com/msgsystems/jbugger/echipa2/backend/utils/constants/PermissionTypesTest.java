package com.msgsystems.jbugger.echipa2.backend.utils.constants;

import com.msgsystems.jbugger.echipa2.backend.utils.constants.PermissionTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermissionTypesTest {

    @Test
    public void testPermissionTypes() {
        assertEquals("PERMISSION_MANAGEMENT", PermissionTypes.PERMISSION_MANAGEMENT);
        assertEquals("USER_MANAGEMENT", PermissionTypes.USER_MANAGEMENT);
        assertEquals("BUG_MANAGEMENT", PermissionTypes.BUG_MANAGEMENT);
        assertEquals("BUG_CLOSE", PermissionTypes.BUG_CLOSE);
    }
}
