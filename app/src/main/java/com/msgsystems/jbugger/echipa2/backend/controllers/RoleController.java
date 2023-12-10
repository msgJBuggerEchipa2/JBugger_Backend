package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.model.RolePermissionPairDTO;
import com.msgsystems.jbugger.echipa2.backend.service.PermissionService;
import com.msgsystems.jbugger.echipa2.backend.service.RoleService;
import com.msgsystems.jbugger.echipa2.backend.service.ServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> findAllRoles(
            @RequestHeader(name="Authorization") String token
    ) throws ServiceOperationException {
      return roleService.findAllRoles().toResponseEntity();
    }

    @RequestMapping(value = "/roles/permissions", method = RequestMethod.PUT)
    public ResponseEntity<Role> addPermissionToRole(
            @RequestHeader(name="Authorization") String token,
            @RequestBody RolePermissionPairDTO body
            ) throws ServiceOperationException {
        var permission = permissionService.findByType(body.getPermissionType()).getValueInterceptError();
        var role = roleService.findByType(body.getRoleType()).getValueInterceptError();
        return roleService.addPermissionToRole(role, permission)
                .toResponseEntity();
    }


}
