package dev.pranay.socialmedia2.exception;

public class PostNotUploadedException extends Exception {

    public PostNotUploadedException() {
        super("Post could not be uploaded");
    }
}
