package com.msgsystems.jbugger.echipa2.backend.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PermissionTest {

    @Test
    void testPermission() {
        Permission permission = new Permission("CREATE");
        assertEquals("CREATE", permission.getType());
        permission.setType("READ");
        assertEquals("READ", permission.getType());
        permission.setDescription("This is a read permission");
        assertEquals("READ", permission.getType());
        assertEquals("This is a read permission", permission.getDescription());
    }
}
