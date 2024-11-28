package services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import pbfProto.Auth;
import pbfProto.Auth.ROLE;
import services.RegistryService;
import models.User;
import models.UserAccountData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistryServiceTest {

    private RegistryService registryService;

    @BeforeAll
    public void setup() throws Exception {
    registryService = new RegistryService();
    }

   /*  @Test
    public void registerFemtechUser() {

        // Given
        String profileName = "Mary";
        String registrationDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String paymentPlan = "Basic";
        Boolean subscriptionStatus = true;
        ROLE role = ROLE.FEMTECH_USER;
        String username = "mary123";
        String password = "password";
  
        User user = registryService.registerUser(profileName, registrationDate, paymentPlan, subscriptionStatus, role, username, password);

        User repoUser = registryService.userRepo.fetchUser(username);

        assertEquals(repoUser, user);
        assertEquals("Mary", user.getUsername());
        assertEquals(ROLE.FEMTECH_USER, user.getRole());
    }

    @Test
    public void registerFemtechServiceProvider() {

        // Given
        String profileName = "Natural Cycles";
        String registrationDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String paymentPlan = "";
        Boolean subscriptionStatus = false;
        ROLE role = ROLE.FEMTECH_PROVIDER;
        String username = "admin";
        String password = "password";

  
        User user = registryService.registerUser(profileName, registrationDate, paymentPlan, subscriptionStatus, role, username, password);

        User repoUser = registryService.userRepo.fetchUser(username);

        assertEquals(repoUser, user);
        assertEquals("Natural Cycles", user.getUsername());
        assertEquals(ROLE.FEMTECH_PROVIDER, user.getRole());
    }

    @Test
    public void registerUserThatAlreadyExists() {

        String profileName = "Mary";
        String registrationDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String paymentPlan = "Basic";
        Boolean subscriptionStatus = true;
        ROLE role = ROLE.FEMTECH_USER;
        String username = "mary123";
        String password = "password";

        User user = registryService.registerUser(profileName, registrationDate, paymentPlan, subscriptionStatus, role, username, password);

        assertNull(user);
    }

    @Test
    public void registerUserWithEmptyRole() {
        User user = registryService.registerUser(
                "Sally",
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()),
                "",
                true,
                null,
                "sally123",
                "anything"
        );

        assertNull(user);
    }
    
    @Test
    public void authenticateUser() {
        String token = registryService.authenticateUser("mary123", "password");
        registryService.logoutUser(token);

        assertNotNull(token);
    }


    @Test
    public void authenticateNonexistentUser() {
        String token = registryService.authenticateUser("Kate123", "password");

        assertNull(token);
    }

    @Test
    public void authenticateUserWithWrongPassword() {
        String token = registryService.authenticateUser("mary123", "wrongpassword");

        assertNull(token);
    }


    @Test
    public void logoutUser() {
        String token = registryService.authenticateUser("mary123", "password");
        boolean result = registryService.logoutUser(token);

        assertTrue(result);
    }

    @Test
    public void logoutNonexistentUser() {
        boolean result = registryService.logoutUser(registryService.generateToken());

        assertFalse(result);
    }

    @Test
    public void deletetUserWithoutAuthorization() {
        String token = registryService.authenticateUser("mary123", "password");
        
        Boolean result = registryService.deleteUser(token);

        assertFalse(result);
    }

    @Test
    public void deletetUser() {
        String token = registryService.authenticateUser("admin", "password");
        Boolean result = registryService.deleteUser(token);

        assertTrue(result);
    }


    @AfterAll
    public void clean() {
        String token = registryService.authenticateUser("mary123", "password");     
        Boolean result = registryService.deleteUser(token);
    } */


}
