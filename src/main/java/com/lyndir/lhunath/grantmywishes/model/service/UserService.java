package com.lyndir.lhunath.grantmywishes.model.service;

import com.google.common.base.Predicate;
import com.lyndir.lhunath.grantmywishes.data.*;
import com.lyndir.lhunath.grantmywishes.error.NoSuchUserException;
import com.lyndir.lhunath.grantmywishes.error.UserNameUnavailableException;
import com.lyndir.lhunath.opal.security.error.PermissionDeniedException;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import com.lyndir.lhunath.opal.wayward.model.WicketInjected;
import org.jetbrains.annotations.NotNull;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public interface UserService extends WicketInjected {

    @NotNull
    User authenticate(@NotNull String userIdentifier)
            throws NoSuchUserException;

    @NotNull
    User newUser(@NotNull String userName)
            throws UserNameUnavailableException;

    @NotNull
    User getUser(@NotNull String userName);

    SizedIterator<Profile> getProfiles(Predicate<Profile> predicate);

    @NotNull
    WishList newWishList(@NotNull String name, Profile profile);

    @NotNull
    WishList getWishList(@NotNull User owner, @NotNull String name);

    Profile getProfile(@NotNull User user)
            throws PermissionDeniedException;

    SizedIterator<WishList> getWishLists(@NotNull User user);

    void save(@NotNull Profile profile);

    void save(@NotNull WishList wishList);
}
