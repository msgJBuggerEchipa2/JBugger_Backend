package com.msgsystems.jbugger.echipa2.backend.controllers;

import com.msgsystems.jbugger.echipa2.backend.auth.AuthenticationService;
import com.msgsystems.jbugger.echipa2.backend.service.NotificationService;
import com.msgsystems.jbugger.echipa2.backend.service.UserService;
import com.msgsystems.jbugger.echipa2.backend.utils.constants.PermissionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationService authService;
    @GetMapping("/notifications")
    public ResponseEntity<?> getAuthenticatedUserNotifications(Authentication auth) {
        System.out.println("/notifications??");
        var user = authService.assertPermission(auth, PermissionTypes.ADDRESSED_USER);
        System.out.println("/heree??");
        return notificationService.findByUser(user).toResponseEntity();
    }

}
