package a3.implementations;

import authentication.UserManager;
import dao.implementation.UserPostDaoImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

//This must be a singleton.
public class UserManagerImpl implements UserManager {

    InputStream inputStream;

    public UserManagerImpl(){}

    public UserManagerImpl(InputStream inputStream){
        this.inputStream = inputStream;
    }

    @Override
    public boolean authenticateUser(String username, String password){
        if(readAuthenticationFile(username, password) != -1)
            return true;
        else
            return false;
    }

    @Override
    public void setResource(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    //Returns user id if credentials are correct, else returns -1.
    private int readAuthenticationFile(String email, String password) {

        JSONTokener jsonTokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(jsonTokener).getJSONObject("sysUsers");

        int result = -1;
        Boolean emailExists = jsonObject.isNull(email);

        if(!emailExists) {
            JSONObject userDetails = jsonObject.getJSONObject(email);

            String hashedPwd = userDetails.getString("pwd");
            String md5Hex = DigestUtils.md5Hex(password).toUpperCase();
            Boolean match = md5Hex.equals(hashedPwd);

            if(match)
                result = userDetails.getInt("userId");
        }

        return result;
    }
}
