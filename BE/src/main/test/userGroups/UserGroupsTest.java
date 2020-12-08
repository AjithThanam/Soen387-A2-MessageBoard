package userGroups;

import lib.UserGroups;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserGroupsTest {

    InputStream inputStream;
    UserGroups userGroups;

    @Before
    public void setUpBeforeClass() {
        userGroups = new UserGroups();

        try {
            inputStream = new FileInputStream("src/main/resources/userbase.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCorrectGroups() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("public");
        jsonArray.put("soen");
        Assert.assertEquals(userGroups.getUserGroup("eduardo.austin@gmail.com", inputStream).toString(), jsonArray.toString());
    }

    @Test
    public void testIncorrectGroups() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("encs");
        jsonArray.put("soen");
        Assert.assertNotEquals(userGroups.getUserGroup("eduardo.austin@gmail.com", inputStream).toString(), jsonArray.toString());
    }

    @Test
    public void testWithSubGroups() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("public");
        jsonArray.put("concordia");
        jsonArray.put("encs");
        Assert.assertEquals(userGroups.getUserGroup("kate.conor@gmail.com", inputStream).toString(), jsonArray.toString());
    }
}
