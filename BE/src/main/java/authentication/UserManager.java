package authentication;

import java.io.InputStream;

public interface UserManager {

    boolean authenticateUser(String username, String password);
    void setResource(InputStream inputStream);
}
