package servlets;

import dao.implementation.FileAttachementDaoImpl;
import dao.implementation.UserPostDaoImpl;
import message.board.entities.FileAttachment;
import message.board.entities.UserPost;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.Date;
import java.util.*;

@WebServlet(name = "servlets.PostDownloadServlet")
public class PostDownloadServlet extends HttpServlet {

    UserPostDaoImpl userPostDao;

    @Override
    public void init() {
        userPostDao = new UserPostDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String stringId = request.getParameter("postID");
        int id = Integer.parseInt(stringId);

        UserPost userPost = userPostDao.getPost(id);

        if(userPost != null){
            FileWriter xmlFileWriter  = new FileWriter(request.getServletContext().getRealPath("")
                    + "/WEB-INF/post.xml");

            writeToXMLFile(xmlFileWriter, userPost);

            response.setContentType("text/xml");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-disposition", "attachment; filename=post.xml");

            try(InputStream inputStream = request.getServletContext().getResourceAsStream("/WEB-INF/post.xml");
                OutputStream outputStream = response.getOutputStream()) {

                byte[] buffer = new byte[1024];

                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) > -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    private void writeToXMLFile(FileWriter fileWriter, UserPost userPost) throws IOException {
        fileWriter.write("<post>" + System.lineSeparator());
        fileWriter.write("    <title>" +  userPost.getTitle().replace(" (edited)", "") + "</title>" + System.lineSeparator());
        fileWriter.write("    <message>" +  userPost.getMessage() + "</message>" + System.lineSeparator());
        fileWriter.write("    <username>" +  userPost.getUsername() + "</username>" + System.lineSeparator());
        fileWriter.write("    <date>" +  userPost.getDateTime().toString().replace('T', ' ') + "</date>" + System.lineSeparator());
        fileWriter.write("    <lastModified>" +  userPost.getLastModified().toString().replace('T', ' ') + "</lastModified>" + System.lineSeparator());
        fileWriter.write("    <group>" +  userPost.getGroup() + "</group>" + System.lineSeparator());
        fileWriter.write("    <hashtags>" + System.lineSeparator());

        List<String> hashtags = userPost.getHashtags();
        if(hashtags != null) {
            for (int i = 0; i < hashtags.size(); i++)
                fileWriter.write("        <hashtag>" + hashtags.get(i) + "</hashtag>" + System.lineSeparator());
        }

        fileWriter.write("    </hashtags>" + System.lineSeparator());
        fileWriter.write("</post>" + System.lineSeparator());
        fileWriter.close();
    }

}