package com.msgsystems.jbugger.echipa2.backend.domain;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RoleTest {

    @Test
    void testRole() {
        Role role = new Role("ADMIN");
        assertEquals("ADMIN", role.getType());
        role.setType("USER");
        assertEquals("USER", role.getType());
        List<Permission> permissions = List.of(new Permission("CREATE"), new Permission("READ"));
        role.setPermissions(permissions);
        assertEquals("USER", role.getType());
        assertEquals(2, role.getPermissions().size());
    }
}