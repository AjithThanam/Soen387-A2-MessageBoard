package userManager;

import authentication.UserManager;
import authentication.UserManagerFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class UserManagerTest {

    UserManager userManager;

    @Before
    public void setUpBeforeClass() {
        InputStream inputStream = null;
        InputStream inputStreamUserbase = null;

        try {
            inputStream = new FileInputStream("src/main/resources/config.properties");
            inputStreamUserbase = new FileInputStream("src/main/resources/userbase.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        userManager = UserManagerFactory.getInstance(inputStream);
        userManager.setResource(inputStreamUserbase);
    }

    @Test
    public void testValidLogin() {
       Assert.assertTrue(userManager.authenticateUser("sean.evan@gmail.com", "pass123"));
    }

    @Test
    public void testInvalidPassword() {
        Assert.assertFalse(userManager.authenticateUser("sean.evan@gmail.com", "wrong"));
    }

    @Test
    public void testNonExistantUser() {
        Assert.assertFalse(userManager.authenticateUser("wrong@gmail.com", "pass123"));
    }

}
