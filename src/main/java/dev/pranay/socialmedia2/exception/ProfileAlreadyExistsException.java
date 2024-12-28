package dev.pranay.socialmedia2.exception;


public class ProfileAlreadyExistsException extends Exception {

    public ProfileAlreadyExistsException(String email) {
        super("Profile already exists with this email " + email);
    }
}
