package com.lyndir.lhunath.grantmywishes.model.service;

import com.lyndir.lhunath.grantmywishes.data.Profile;
import com.lyndir.lhunath.grantmywishes.data.User;
import com.lyndir.lhunath.grantmywishes.error.NoSuchUserException;
import com.lyndir.lhunath.grantmywishes.error.UserNameUnavailableException;
import com.lyndir.lhunath.opal.security.error.PermissionDeniedException;
import org.jetbrains.annotations.NotNull;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public interface UserService {

    @NotNull
    User authenticate(@NotNull String userIdentifier)
            throws NoSuchUserException;

    @NotNull
    User newUser(@NotNull String userName)
            throws UserNameUnavailableException;

    Profile getProfile(User user)
            throws PermissionDeniedException;
}
