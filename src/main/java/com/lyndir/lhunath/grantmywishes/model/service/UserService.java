package com.lyndir.lhunath.grantmywishes.model.service;

import com.lyndir.lhunath.grantmywishes.data.Profile;
import com.lyndir.lhunath.grantmywishes.data.User;
import com.lyndir.lhunath.grantmywishes.error.NoSuchUserException;
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
    User newUser(@NotNull String userName);

    Profile getProfile(User user);
}
