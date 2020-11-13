package servlets;

import dao.implementation.UserPostDaoImpl;
import message.board.entities.UserPost;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "servlets.UserPostServlet")
public class UserPostServlet extends HttpServlet {

    UserPostDaoImpl userPostDao;

    @Override
    public void init() {
        userPostDao = new UserPostDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String hashtag = request.getParameter("hashtag");
        String startDate = request.getParameter("start");
        String endDate = request.getParameter("end");

        Date dateStart = null;
        Date dateEnd = null;
        if(startDate != null && startDate != "")
            dateStart = Date.valueOf(startDate);

        if(endDate != null && endDate != "")
            dateEnd = Date.valueOf(endDate);

        List<UserPost> posts = null;
        try {
            posts = userPostDao.getPosts(username, hashtag, dateStart, dateEnd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();
        JSONArray postJson = new JSONArray();
        for (UserPost post: posts) {
            JSONObject postInfoJson = new JSONObject();
            postInfoJson.put("postId", post.getPostId());
            postInfoJson.put("title", post.getTitle());
            postInfoJson.put("message", post.getMessage());
            postInfoJson.put("username", post.getUsername());
            postInfoJson.put("dateTime", post.getDateTime());
            postInfoJson.put("lastModified", post.getLastModified());
            postInfoJson.put("hashtag", post.getHashtags());
            postJson.put(postInfoJson);
        }
        jsonResponse.put("posts", postJson);
        PrintWriter writer = response.getWriter();
        writer.append(jsonResponse.toString());
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String stringId = request.getParameter("id");
        int id = Integer.parseInt(stringId);

        boolean databaseResponse = userPostDao.deletePost(id);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", databaseResponse);

        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append(jsonResponse.toString());
    }
}
