package dev.pranay.socialmedia2.service;

import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.exception.ProfileAlreadyExistsException;

public interface ProfileService {
public boolean signUp(Profile profile) throws ProfileAlreadyExistsException;
}
