package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.domain.Role;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.RoleRepository;
import com.msgsystems.jbugger.echipa2.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public ServiceOperationResult<User> findById(Long id){
        var user = userRepository.findById(id);
        if(user.isEmpty())
            return ServiceOperationResult.NotFound((User)null)
                    .setMessage("Could not find user with id " + id);
        return ServiceOperationResult.Ok(user.get());
    }

    public ServiceOperationResult<User> findByUsername(String username){
        var user = userRepository.findByUsername(username);
        if(user.isEmpty())
            return ServiceOperationResult.NotFound((User)null)
                    .setMessage("Could not find user with username '"+username+"'");
        return ServiceOperationResult.Ok(user.get());
    }

    public ServiceOperationResult<User> addRoleToUser(User user, Role role) {
        if(user==null)
            return ServiceOperationResult.Error(user).setMessage("User is null");
        if(role==null)
            return ServiceOperationResult.Error(user).setMessage("Role is null");

        if(user.getRoles().stream().anyMatch(r-> r.getId() == role.getId())) {
            return ServiceOperationResult.NoEffect(user)
                    .setMessage("The user already has this role");
        }
        user.getRoles().add(role);
        userRepository.save(user);
        return ServiceOperationResult.Ok(user);
    }


}
