package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.auth.AuthenticationService;
import com.msgsystems.jbugger.echipa2.backend.auth.NotPermittedException;
import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.model.RolePermissionPairDTO;
import com.msgsystems.jbugger.echipa2.backend.service.PermissionService;
import com.msgsystems.jbugger.echipa2.backend.service.RoleService;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationException;
import com.msgsystems.jbugger.echipa2.backend.utils.constants.PermissionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    AuthenticationService authService;

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> findAllRoles(
            @RequestHeader(name="Authorization") String token
    ) throws ServiceOperationException {
      return roleService.findAllRoles().toResponseEntity();
    }

    @PutMapping(value = "/roles/permissions")
    public ResponseEntity<Role> addPermissionToRole(
            Authentication auth,
            @RequestBody RolePermissionPairDTO body
            ) throws ServiceOperationException {
        authService.assertPermission(auth, PermissionTypes.PERMISSION_MANAGEMENT);
        var permission = permissionService.findByType(body.getPermissionType()).getValueInterceptError();
        var role = roleService.findByType(body.getRoleType()).getValueInterceptError();
        return roleService.addPermissionToRole(role, permission)
                .toResponseEntity();
    }

    @DeleteMapping("/roles/permissions")
    public ResponseEntity<Role> removePermissionToRole(
            Authentication auth,
            @RequestBody RolePermissionPairDTO body
    ) throws ServiceOperationException, NotPermittedException {
        authService.assertPermission(auth, PermissionTypes.PERMISSION_MANAGEMENT);
        var permission = permissionService.findByType(body.getPermissionType()).getValueInterceptError();
        var role = roleService.findByType(body.getRoleType()).getValueInterceptError();
        return roleService.removePermissionFromRole(role, permission)
                .toResponseEntity();
    }


}
