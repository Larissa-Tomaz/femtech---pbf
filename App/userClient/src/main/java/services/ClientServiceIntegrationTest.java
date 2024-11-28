/*import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pbfProto.Auth;
import services.ClientService;
import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceIntegrationTest {

    private ClientService clientService;
    private String userToken;

    @BeforeEach
    public void setUp() throws Exception { 
        clientService = new ClientService("localhost", 8080);     
    }

    @Test
    public void testRegisterLoginLogout() throws Exception {

        // Step 1: Register a new user
        System.out.println("Registering a new user...");
        clientService.registerUser("newTestUser", "New Test Profile", "Basic", true, "testPassword123");
        
        // Ensure the user is registered with a token
        assertNotNull(clientService.userToken, "User token should not be null after registration");
        System.out.println("User registered successfully with token: " + clientService.userToken);

        // Step 2: Log out the user
        System.out.println("Logging out the user...");
        clientService.logoutUser();
        assertNull(clientService.userToken, "User token should be null after logout");

        // Step 3: Log in with the registered user
        System.out.println("Logging in with the registered user...");
        clientService.loginUser("newTestUser", "testPassword123");
        assertNotNull(clientService.userToken, "User token should not be null after login");
        System.out.println("User logged in successfully with token: " + clientService.userToken);

        // Step 3: Delete the user
        System.out.println("Deleting the user...");
        clientService.deletetUser("newTestUser");

        // Ensure the token is invalidated (set to null) after deleting user
        assertNull(clientService.userToken, "User token should be null after deletion");
        System.out.println("User Deleted successfully.");
    }
}*/
