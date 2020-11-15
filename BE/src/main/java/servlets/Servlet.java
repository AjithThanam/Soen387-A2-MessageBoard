package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

@WebServlet(name = "servlets.Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int result = -1;

        if(email != null && password != null) {
            result = authenticateUser(request, email, password);
        }
        sendResponse(response, result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HelloWorldServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HelloWorldServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException exception){
            exception.getMessage();
        }

    }

    //Returns user id if credentials are correct, else returns -1.
    private int authenticateUser(HttpServletRequest request, String email, String password) {

        String filePath = "/WEB-INF/userbase.json";
        InputStream inputStream = request.getServletContext().getResourceAsStream(filePath);

        JSONTokener jsonTokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(jsonTokener);

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

    private void sendResponse(HttpServletResponse response, int result) throws IOException{
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();
        if(result != -1) {
            jsonResponse.put("success", "true");
            JSONObject data = new JSONObject();
            data.put("userID" , result);
            //replace with actual session ID
            data.put("sessionID", "FAKESESSIONID");
            jsonResponse.put("data", data);
        }else{
            jsonResponse.put("success", "false");
        }

        PrintWriter writer = response.getWriter();
        writer.append(jsonResponse.toString());
    }
}
