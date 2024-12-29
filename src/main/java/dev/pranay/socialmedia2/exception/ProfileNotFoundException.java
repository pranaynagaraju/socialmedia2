package dev.pranay.socialmedia2.exception;

public class ProfileNotFoundException extends Exception {

    public ProfileNotFoundException() {
        super("The profile you are looking for doesn't exists");
    }
}
