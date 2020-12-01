package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import authentication.UserManager;
import authentication.UserManagerFactory;
import lib.UserGroups;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

@WebServlet(name = "servlets.Servlet")
public class Servlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean result = false;

        if(email != null && password != null) {
            String filePath = "/WEB-INF/userbase.json";
            InputStream inputStream = request.getServletContext().getResourceAsStream(filePath);
            UserManager userManager = UserManagerFactory.getInstance();
            userManager.setResource(inputStream);
            result = userManager.authenticateUser(email, password);
        }
        JSONArray groups = new JSONArray();

        if(result){
            groups = new UserGroups().getUserGroup(email, request);
        }

        sendResponse(response, result, groups);
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

    private void sendResponse(HttpServletResponse response, boolean result, JSONArray groups) throws IOException{
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();
        if(result) {
            jsonResponse.put("success", "true");
            JSONObject data = new JSONObject();
            //replace with actual session ID
            data.put("sessionID", "FAKESESSIONID");
            data.put("groups", groups);
            jsonResponse.put("data", data);
        }else{
            jsonResponse.put("success", "false");
        }

        PrintWriter writer = response.getWriter();
        writer.append(jsonResponse.toString());
    }
}
