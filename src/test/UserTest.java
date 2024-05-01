import com.example.digitaldetox.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private User user;
    @BeforeEach
    public void setUp() {
        user = new User("testSuiteUser", "testSuitePassword", "testSuiteUser@gmail.com");
    }

    @Test
    public void testGetId() {
        user.setaccountId(10);
        Assertions.assertEquals(10, user.getaccountId());
    }
    @Test
    public void testGetUsername() {
        Assertions.assertEquals("testSuiteUser", user.getUsername());
    }
    @Test
    public void testSetUsername() {
        user.setUsername("changedUsername");
        Assertions.assertEquals("changedUsername", user.getUsername());
    }
    @Test
    public void testGetPassword() {
        Assertions.assertEquals("testSuitePassword", user.getPassword());
    }
    @Test
    public void testSetPassword() {
        user.setPassword("changedPassword");
        Assertions.assertEquals("changedPassword", user.getPassword());
    }
    @Test
    public void testGetEmail() {
        Assertions.assertEquals("testSuiteUser@gmail.com", user.getEmail());
    }
    @Test
    public void testSetEmail() {
        user.setEmail("changedEmail@gmail.com");
        Assertions.assertEquals("changedEmail@gmail.com", user.getEmail());
    }
    @Test
    public void testGetAccountDetails() {
        Assertions.assertEquals("Username: " + "testSuiteUser" + "\n" + "Password: " + "testSuitePassword" + "Email: " + "testSuiteUser@gmail.com" + "\n"
                , user.getAccountDetails());
    }
}
