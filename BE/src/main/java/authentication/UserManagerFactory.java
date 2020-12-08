package authentication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserManagerFactory {

    private static UserManager userManager;

    private UserManagerFactory(){}

    public static UserManager getInstance(InputStream inputStream){
        if(userManager == null){
            synchronized (UserManagerFactory.class){
                if(userManager == null){
                   userManager = createInstance(inputStream);
                }
            }
        }
        return userManager;
    }

    private static UserManager createInstance(InputStream inputStream){
        Class c = null;
        try {
            c = Class.forName(getUserManagerClass(inputStream));
            UserManager userManager = (UserManager) c.newInstance();
            return userManager;
        } catch (Exception e) {
            return null;
        }
    }

    private static String getUserManagerClass(InputStream inputStream){
        Properties prop = new Properties();

        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop.getProperty("userManagerClass");
    }

}
