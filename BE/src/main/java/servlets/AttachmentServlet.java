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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.Date;
import java.util.*;

public class AttachmentServlet extends HttpServlet {

    UserPostDaoImpl userPostDao;
    FileAttachementDaoImpl fileAttachmentDao;

    @Override
    public void init() {
        userPostDao = new UserPostDaoImpl();
        fileAttachmentDao = new FileAttachementDaoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HashMap<String, Object> payload = getPayload(request);

        boolean dbResponse;
        //If no ID is given assume it is a new post, if ID is given update existing post.
        if (payload.get("postID") == null){
            UserPost up = new UserPost((String) payload.get("title"), (String) payload.get("message"),
                    (String) payload.get("userID"), (String) payload.get("group"));
            dbResponse = userPostDao.insertPost(up);

            if(((List)payload.get("files")).size() != 0 && dbResponse){
                FileAttachment fileAttachment = (FileAttachment) ((List)payload.get("files")).get(0);
                fileAttachment.setPostId(up.getPostId());
                dbResponse = fileAttachmentDao.insertAttachment(fileAttachment);
            }

        }else{
            int id = Integer.parseInt((String)payload.get("postID"));
            String updatedTitle = (String) payload.get("title");

            if(!updatedTitle.contains("(edited)")){
                updatedTitle = updatedTitle + " (edited)";
            }

            UserPost userPost = new UserPost(id, updatedTitle, (String) payload.get("message"),
                    (String) payload.get("userID"), null, new Date(Calendar.getInstance().getTime().getTime()),
                    (String) payload.get("group"));
            dbResponse = userPostDao.updatePost(userPost);

            if(payload.get("removeAttach") != null) {
                dbResponse = fileAttachmentDao.deleteAttachment(id);
            }else{
                if(((List)payload.get("files")).size() != 0 && dbResponse){
                    FileAttachment fileAttachment = (FileAttachment) ((List)payload.get("files")).get(0);
                    fileAttachment.setPostId(id);
                    //check if there is an existing file for this post
                    FileAttachment existingFileAttachment = fileAttachmentDao.getAttachment(id);
                    //if this post has an attachment, edit it else create a new attachment
                    if(existingFileAttachment != null)
                        dbResponse = fileAttachmentDao.updateAttachment(fileAttachment);
                    else
                        dbResponse = fileAttachmentDao.insertAttachment(fileAttachment);
                }
            }
        }

        int i =0;

        payload.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });

        sendBackResponse(response, dbResponse);
    }

    private HashMap<String, Object> getPayload(HttpServletRequest request){
        HashMap<String, Object> requestData = new HashMap<>();
        List<FileAttachment> uploadedFiles = new ArrayList<>();
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
                            if(item.getSize() <= 0)
                                continue;
                            String fileName = new File(item.getName()).getName();

                            //Use for testing
                            //String filePath = "C:\\Users\\Home\\Desktop\\test_dump" + File.separator + fileName;
                            String filePath = fileName;
                            //File fileData = new File(filePath);

                            FileAttachment fileInput = new FileAttachment();
                            fileInput.setFilename(fileName);
                            fileInput.setFilesize(item.getSize() + "");
                            fileInput.setMediaType(fileName.split("\\.")[1]);
                            fileInput.setMedia(item.get());

                            uploadedFiles.add(fileInput);

                            ////Use for testing
                            //item.write(fileData);
                        }
                        else{

                            String key = item.getFieldName();
                            String value = item.getString().replaceAll("<.*?>", "");
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

        String stringId = request.getParameter("postID");
        int id = Integer.parseInt(stringId);

        FileAttachment fileAttachment = fileAttachmentDao.getAttachment(id);

        if(fileAttachment != null){
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-disposition", "attachment; filename=" + fileAttachment.getFilename());

            InputStream inputStream = new ByteArrayInputStream(fileAttachment.getMedia());
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outStream.close();
        }
    }
}