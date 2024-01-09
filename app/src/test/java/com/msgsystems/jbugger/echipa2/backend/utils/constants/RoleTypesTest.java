package com.msgsystems.jbugger.echipa2.backend.utils.constants;

import com.msgsystems.jbugger.echipa2.backend.utils.constants.RoleTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTypesTest {

    @Test
    public void testRoleTypes() {
        assertEquals("Administrator", RoleTypes.ADMINISTRATOR);
        assertEquals("Project_manager", RoleTypes.PROJECT_MANAGER);
        assertEquals("Test_manager", RoleTypes.TEST_MANAGER);
        assertEquals("Tester", RoleTypes.TESTER);
        assertEquals("Developer", RoleTypes.DEVELOPER);
    }
}