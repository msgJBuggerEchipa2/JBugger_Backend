package com.msgsystems.jbugger.echipa2.backend;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.repository.PermissionRepository;
import com.msgsystems.jbugger.echipa2.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public ApplicationLoader(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    private void create_role_if_not_exists(String role_type){
        if(roleRepository.findByType(role_type).isEmpty()){
            Role role=new Role(role_type);
            roleRepository.save(role);
        }
    }

    private void create_permission_if_not_exists(String type, String description) {
        var existingPermission = permissionRepository.findByType(type);
        if(existingPermission.isEmpty()){
            permissionRepository.save(new Permission(type, description));
        } else {
            var perm = existingPermission.get();
            perm.setDescription(description);
            permissionRepository.save(perm);
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        create_role_if_not_exists("Administrator");
        create_role_if_not_exists("Project manager");
        create_role_if_not_exists("Test manager");
        create_role_if_not_exists("Developer");
        create_role_if_not_exists("Tester");

        create_permission_if_not_exists("PERMISSION_MANAGEMENT", "Administreaza permisiuni");
        create_permission_if_not_exists("USER_MANAGEMENT", "Se ocupa de conturi");
        create_permission_if_not_exists("BUG_MANAGEMENT", "Cei care gestioneaza buguri");
        create_permission_if_not_exists("BUG_CLOSE", "Cei care inchid buguri");
    }
}
