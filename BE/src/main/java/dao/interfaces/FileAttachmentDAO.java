package dao.interfaces;

import message.board.entities.FileAttachment;

public interface FileAttachmentDAO {
    boolean insertAttachment(FileAttachment attachment);
    boolean updateAttachment(FileAttachment attachment);
    boolean deleteAttachment(int id);
    FileAttachment getAttachment(int postID);
}
