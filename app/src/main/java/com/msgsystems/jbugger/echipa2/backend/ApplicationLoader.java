package com.msgsystems.jbugger.echipa2.backend;

import com.msgsystems.jbugger.echipa2.backend.auth.AuthenticationService;
import com.msgsystems.jbugger.echipa2.backend.auth.RegisterUserDto;
import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.repository.PermissionRepository;
import com.msgsystems.jbugger.echipa2.backend.repository.RoleRepository;
import com.msgsystems.jbugger.echipa2.backend.repository.UserRepository;
import com.msgsystems.jbugger.echipa2.backend.utils.constants.PermissionTypes;
import com.msgsystems.jbugger.echipa2.backend.utils.constants.RoleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLoader implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authService;

    @Autowired
    public ApplicationLoader(RoleRepository roleRepository, PermissionRepository permissionRepository, UserRepository userRepository, AuthenticationService authService) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.authService = authService;
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
        create_role_if_not_exists(RoleTypes.ADMINISTRATOR);
        create_role_if_not_exists(RoleTypes.PROJECT_MANAGER);
        create_role_if_not_exists(RoleTypes.TEST_MANAGER);
        create_role_if_not_exists(RoleTypes.DEVELOPER);
        create_role_if_not_exists(RoleTypes.TESTER);

        create_permission_if_not_exists(PermissionTypes.PERMISSION_MANAGEMENT, "Administreaza permisiuni");
        create_permission_if_not_exists(PermissionTypes.USER_MANAGEMENT, "Se ocupa de conturi");
        create_permission_if_not_exists(PermissionTypes.BUG_MANAGEMENT, "Cei care gestioneaza buguri");
        create_permission_if_not_exists(PermissionTypes.BUG_CLOSE, "Cei care inchid buguri");

        // give admin all permissions
        var adminRole = roleRepository.findByType(RoleTypes.ADMINISTRATOR).orElseThrow();
        permissionRepository.findAll().forEach(p->{
            if(adminRole.getPermissions().stream().anyMatch(ap->ap.getId()==p.getId()))
                return;
            adminRole.getPermissions().add(p);
        });
        roleRepository.save(adminRole);

        // create admin user

        var admin = userRepository.findByUsername("admin")
                .orElseGet(() -> authService
                        .signup(new RegisterUserDto("admin", "admin", "admin")));

        System.out.println("Admin = "+admin);
        if(admin.getRoles().stream().noneMatch(r->r.getId()==adminRole.getId())){
            admin.getRoles().add(adminRole);
        }
        System.out.println("Admin = "+admin);

        userRepository.save(admin);
    }
}
