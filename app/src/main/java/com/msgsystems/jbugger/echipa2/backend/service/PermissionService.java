package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.domain.Permission;
import com.msgsystems.jbugger.echipa2.backend.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public ServiceOperationResult<List<Permission>> findAll(){
        return ServiceOperationResult.Ok(permissionRepository.findAll());
    }

    public ServiceOperationResult<Permission> findByType(String type){
        var perm = permissionRepository.findByType(type);
        if(perm.isEmpty())
            return ServiceOperationResult.NotFound((Permission)null)
                    .setMessage("Could not find permission with type '"+type+"'");
        return ServiceOperationResult.Ok(perm.get());
    }

}
