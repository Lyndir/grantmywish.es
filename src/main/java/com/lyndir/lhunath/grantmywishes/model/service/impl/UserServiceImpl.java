package com.lyndir.lhunath.grantmywishes.model.service.impl;

import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.*;
import com.lyndir.lhunath.grantmywishes.data.service.UserDAO;
import com.lyndir.lhunath.grantmywishes.error.*;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.opal.security.error.PermissionDeniedException;
import org.jetbrains.annotations.NotNull;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Inject
    public UserServiceImpl(final UserDAO userDAO) {

        this.userDAO = userDAO;
    }

    @NotNull
    @Override
    public User authenticate(@NotNull final String userIdentifier)
            throws NoSuchUserException {

        User user = userDAO.findUser( userIdentifier );
        if (user == null)
            throw new NoSuchUserException( userIdentifier );

        // TODO: authenticate the user.

        return user;
    }

    @NotNull
    @Override
    public User newUser(@NotNull final String userName)
            throws UserNameUnavailableException {

        if (userDAO.findUser( userName ) != null)
            throw new UserNameUnavailableException( userName );

        return userDAO.update( new User( userName ) );
    }

    @Override
    public Profile getProfile(final User user)
            throws PermissionDeniedException {

        return userDAO.getProfile( user );
    }
}
