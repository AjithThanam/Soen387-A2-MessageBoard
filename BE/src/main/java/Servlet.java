import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

    private boolean authenticateUser(HttpServletRequest request, String email, String password) {

        String filePath = "/WEB-INF/userbase.json";
        InputStream inputStream = request.getServletContext().getResourceAsStream(filePath);

        JSONTokener jsonTokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(jsonTokener);

        Boolean result = false;
        Boolean emailExists = jsonObject.isNull(email);

        if(!emailExists) {
            JSONObject userDetails = jsonObject.getJSONObject(email);

            String hashedPwd = userDetails.getString("pwd");
            String md5Hex = DigestUtils.md5Hex(password).toUpperCase();
            result = md5Hex.equals(hashedPwd);
        }

        return result;
    }
}
