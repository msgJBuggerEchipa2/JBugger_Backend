package com.msgsystems.jbugger.echipa2.backend;

import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLoader implements ApplicationRunner {
    private RoleRepository roleRepository;

    @Autowired
    public ApplicationLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private void create_role_if_not_exists(String role_type){
        if(roleRepository.findByType(role_type).isEmpty()){
            Role role=new Role(role_type);
            roleRepository.save(role);
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        create_role_if_not_exists("Administrator");
        create_role_if_not_exists("Project manager");
        create_role_if_not_exists("Test manager");
        create_role_if_not_exists("Developer");
        create_role_if_not_exists("Tester");
    }
}
