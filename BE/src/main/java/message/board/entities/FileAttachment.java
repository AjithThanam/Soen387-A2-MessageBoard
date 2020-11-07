package message.board.entities;

public class FileAttachment {
    private static int id;
    private String filename;
    private String filesize;
    private String mediaType;
    private byte[] media;
    private int postId;

    public FileAttachment(String filename, String filesize, String mediaType, byte[] media, int postId) {
        this.id += 1;
        this.filename = filename;
        this.filesize = filesize;
        this.mediaType = mediaType;
        this.media = media;
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getMedia() {
        return media;
    }

    public void setMedia(byte[] media) {
        this.media = media;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
