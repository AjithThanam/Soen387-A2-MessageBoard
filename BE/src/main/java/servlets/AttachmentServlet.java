package servlets;

import dao.implementation.UserPostDaoImpl;
import message.board.entities.UserPost;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttachmentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HashMap<String, Object> payload = getPayload(request);
        UserPostDaoImpl userPostDao = new UserPostDaoImpl();

        UserPost up = new UserPost((String)payload.get("title"), (String)payload.get("message"), (String)payload.get("userID"));

        boolean dbResponse = userPostDao.insertPost(up);

        int i =0;

        payload.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });

        sendBackResponse(response, dbResponse);
    }

    private HashMap<String, Object> getPayload(HttpServletRequest request){
        HashMap<String, Object> requestData = new HashMap<>();
        List<File> uploadedFiles = new ArrayList<>();
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1000000);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);

            if (ServletFileUpload.isMultipartContent(request)) {
                List<FileItem> formItems = null;
                formItems = upload.parseRequest(request);
                if (formItems != null && formItems.size() > 0) {
                    for (FileItem item : formItems) {
                        if (!item.isFormField()) {
                            String fileName = new File(item.getName()).getName();

                            //Use for testing
                            //String filePath = "C:\\Users\\Home\\Desktop\\test_dump" + File.separator + fileName;
                            String filePath = fileName;
                            File fileData = new File(filePath);
                            uploadedFiles.add(fileData);

                            ////Use for testing
                            //item.write(fileData);
                        }
                        else{

                            String key = item.getFieldName();
                            String value = item.getString();
                            requestData.put(key, value);
                        }
                    }
                    requestData.put("files", uploadedFiles);
                }
            }
            return requestData;
        }catch (Exception e){
            return null;
        }
    }


    private void sendBackResponse(HttpServletResponse response, boolean success) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Content-Type", "application/json");

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", success);
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>servlets.Servlet HelloWorldServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>servlets.Servlet HelloWorldServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException exception){
            exception.getMessage();
        }
    }
}