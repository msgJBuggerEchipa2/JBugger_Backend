import com.msgsystems.jbugger.echipa2.backend.auth.AuthenticationService;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.UserRepository;
import com.msgsystems.jbugger.echipa2.backend.service.RoleService;
import com.msgsystems.jbugger.echipa2.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Test
    public void testFindAllUsers() {
        // Create mocks
        UserRepository userRepository = mock(UserRepository.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        RoleService roleService = mock(RoleService.class);
        UserService userService = mock(UserService.class);

        // Create instance of the controller to test
        UserController userController = new UserController(userRepository, authService, roleService, userService);

        // Mock the behavior of the findAll method
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        // Test the findAll method
        ResponseEntity<List<User>> response = userController.findAll();

        // Verify the result
        assertEquals(users, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddUser() {
        // Create mocks
        UserRepository userRepository = mock(UserRepository.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        RoleService roleService = mock(RoleService.class);
        UserService userService = mock(UserService.class);

        // Create instance of the controller to test
        UserController userController = new UserController(userRepository, authService, roleService, userService);

        // Mock the behavior of the addUser method
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@msggroup.com");
        user.setMobileNumber("+4012345678");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Test the addUser method
        ResponseEntity<String> response = userController.addUser(user);

        // Verify the result
        assertEquals("User added successfully", response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    public void testValidateUser() {
        // Create mocks
        UserRepository userRepository = mock(UserRepository.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        RoleService roleService = mock(RoleService.class);
        UserService userService = mock(UserService.class);

        // Create instance of the controller to test
        UserController userController = new UserController(userRepository, authService, roleService, userService);

        // Test the validateUser method
        User validUser = new User();
        validUser.setFirstName("John");
        validUser.setLastName("Doe");
        validUser.setEmail("john.doe@msggroup.com");
        validUser.setMobileNumber("+4012345678");
        boolean isValid = userController.validateUser(validUser);

        // Verify the result
        assertEquals(true, isValid);

        // Test the validateUser method with an invalid user
        User invalidUser = new User();
        invalidUser.setFirstName("John1"); // Invalid first name
        invalidUser.setLastName("Doe");
        invalidUser.setEmail("john.doe@msggroup.com");
        invalidUser.setMobileNumber("+4012345678");
        isValid = userController.validateUser(invalidUser);

        // Verify the result
        assertEquals(false, isValid);
    }

    public void testCreateUser() {
        // Create mocks
        UserRepository userRepository = mock(UserRepository.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        RoleService roleService = mock(RoleService.class);
        UserService userService = mock(UserService.class);

        // Create instance of the controller to test
        UserController userController = new UserController(userRepository, authService, roleService, userService);

        // Mock the behavior of the createUser method
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@msggroup.com");
        user.setMobileNumber("+4012345678");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Test the createUser method
        userController.createUser(user);

        // Verify the result
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() {
        // Create mocks
        UserRepository userRepository = mock(UserRepository.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        RoleService roleService = mock(RoleService.class);
        UserService userService = mock(UserService.class);

        // Create instance of the controller to test
        UserController userController = new UserController(userRepository, authService, roleService, userService);

        // Mock the behavior of the deleteUser method
        doNothing().when(userRepository).deleteById(any(Long.class));

        // Test the deleteUser method
        userController.deleteUser(1L);

        // Verify the result
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testEditUser() {
        // Create mocks
        UserRepository userRepository = mock(UserRepository.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        RoleService roleService = mock(RoleService.class);
        UserService userService = mock(UserService.class);

        // Create instance of the controller to test
        UserController userController = new UserController(userRepository, authService, roleService, userService);

        // Mock the behavior of the editUser method
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@msggroup.com");
        user.setMobileNumber("+4012345678");
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Test the editUser method
        User editedUser = new User();
        editedUser.setFirstName("Jane");
        editedUser.setLastName("Doe");
        editedUser.setEmail("jane.doe@msggroup.com");
        editedUser.setMobileNumber("+4012345678");
        userController.editUser(1L, editedUser);

        // Verify the result
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(editedUser);
    }

    @Test
    public void testAddRole() {
        // Create mocks
        UserRepository userRepository = mock(UserRepository.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        RoleService roleService = mock(RoleService.class);
        UserService userService = mock(UserService.class);

        // Create instance of the controller to test
        UserController userController = new UserController(userRepository, authService, roleService, userService);

        // Mock the behavior of the addRole method
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@msggroup.com");
        user.setMobileNumber("+4012345678");
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        doNothing().when(roleService).addRole(any(User.class), any(String.class));

        // Test the addRole method
        userController.addRole(1L, "ADMIN");

        // Verify the result
        verify(userRepository, times(1)).findById(1L);
        verify(roleService, times(1)).addRole(user, "ADMIN");
    }

    @Test
    public void testSendWelcomeNotification() {
        // Create mocks
        UserRepository userRepository = mock(UserRepository.class);
        AuthenticationService authService = mock(AuthenticationService.class);
        RoleService roleService = mock(RoleService.class);
        UserService userService = mock(UserService.class);
        NotificationService notificationService = mock(NotificationService.class);

        // Create instance of the controller to test
        UserController userController = new UserController(userRepository, authService, roleService, userService,
                notificationService);

        // Mock the behavior of the sendWelcomeNotification method
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@msggroup.com");
        user.setMobileNumber("+4012345678");
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        doNothing().when(notificationService).sendWelcomeNotification(any(User.class));

        // Test the sendWelcomeNotification method
        userController.sendWelcomeNotification(1L);

        // Verify the result
        verify(userRepository, times(1)).findById(1L);
        verify(notificationService, times(1)).sendWelcomeNotification(user);
    }
}
