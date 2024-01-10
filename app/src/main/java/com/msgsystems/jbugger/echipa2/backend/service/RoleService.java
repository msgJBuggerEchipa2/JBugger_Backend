package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.repository.PermissionRepository;
import com.msgsystems.jbugger.echipa2.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;

    /**
     * gets a list of all roles
     * @return
     */
    public ServiceOperationResult<List<Role>> findAllRoles(){
        return ServiceOperationResult.Ok(roleRepository.findAll());
    }

    /**
     * gets a role by given type
     * @param type name of type (e.g. "Administrator")
     * @return operation result containing either the role entity, or null if not found
     */
    public ServiceOperationResult<Role> findByType(String type){
        var role = roleRepository.findByType(type);
        if(role.isEmpty())
            return ServiceOperationResult.NotFound((Role)null)
                    .setMessage("Could not find role with type '"+type+"'");
        return ServiceOperationResult.Ok(role.get());
    }


    /**
     * Adds one permission to a given role. If the role is already granted
     * that permission, nothing happens.
     * @param role target role
     * @param permission permission to add
     * @return the status of the operation
     * @apiNote JBG-001
     */
    public ServiceOperationResult<Role> addPermissionToRole(Role role, Permission permission) {
        if(role==null)
            return ServiceOperationResult.Error(role).setMessage("Role is null");
        if(permission==null)
            return ServiceOperationResult.Error(role).setMessage("Permission is null");

        if(role.getPermissions().stream().anyMatch(p-> p.getId() == permission.getId())) {
            return ServiceOperationResult.NoEffect(role)
                    .setMessage("The role already this permission");
        }

        Optional<Permission> existingPermission = role.getPermissions().stream()
                .filter(p -> p.getType().equals(permission.getType()))
                .findFirst();

        if (existingPermission.isPresent()) {
            // Permission with the same type already exists, update or handle it as needed
            return ServiceOperationResult.NoEffect(role)
                    .setMessage("The role already has a permission with the same type");
        }

        role.getPermissions().add(permission);
        roleRepository.save(role);
        return ServiceOperationResult.Ok(role);
    }

    /**
     * Removes one permission from a given role. If the role does not have
     * that permission, nothing happens.
     * @param role target role
     * @param permission permission to remove
     * @return the status of the operation
     * @apiNote JBG-002
     */
    public ServiceOperationResult<Role> removePermissionFromRole(Role role, Permission permission){
        if(role==null)
            return ServiceOperationResult.Error(role).setMessage("Role is null");
        if(permission==null)
            return ServiceOperationResult.Error(role).setMessage("Permission is null");

        if(role.getPermissions().stream().noneMatch(p-> p.getId() == permission.getId())) {
            return ServiceOperationResult.NoEffect(role)
                    .setMessage("The role does not have this permission");
        }
        role.getPermissions().removeIf(p->p.getId() == permission.getId());
        roleRepository.save(role);
        return ServiceOperationResult.Ok(role);
    }
}
