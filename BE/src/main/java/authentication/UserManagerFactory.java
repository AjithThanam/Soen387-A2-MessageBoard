package authentication;

public class UserManagerFactory {

    private static UserManager userManager;

    private UserManagerFactory(){}

    public static UserManager getInstance(){
        if(userManager == null){
            synchronized (UserManagerFactory.class){
                if(userManager == null){
                   userManager = createInstance();
                }
            }
        }
        return userManager;
    }

    private static UserManager createInstance(){
        //change to read from config file
        Class c = null;
        try {
            c = Class.forName("a3.implementations.UserManagerImpl");
            UserManager userManager = (UserManager) c.newInstance();
            return userManager;
        } catch (Exception e) {
            return null;
        }
    }

}
