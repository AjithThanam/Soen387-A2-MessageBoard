package lib;

import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserGroups {

    public JSONArray getUserGroup(String email, HttpServletRequest request){
        String filePath = "/WEB-INF/userbase.json";
        InputStream inputStream = request.getServletContext().getResourceAsStream(filePath);

        JSONTokener jsonTokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(jsonTokener).getJSONObject("sysUsers");

        JSONObject userInfo = jsonObject.getJSONObject(email);
        JSONArray groups = userInfo.getJSONArray("groups");

        return groups;
    }

    public List<String> toList(JSONArray groups){
        List<String> groupsArr = new ArrayList<>();

        for(int i =0; i < groups.length(); i++){
            groupsArr.add(groups.getString(i));
        }
        return groupsArr;
    }

    public JSONArray getUserGroup(String email, InputStream inputStream){
        JSONTokener jsonTokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(jsonTokener).getJSONObject("sysUsers");

        JSONObject userInfo = jsonObject.getJSONObject(email);
        JSONArray groups = userInfo.getJSONArray("groups");

        return groups;
    }
}
