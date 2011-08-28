package com.lyndir.lhunath.grantmywishes.data.service;

import com.lyndir.lhunath.grantmywishes.data.*;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public interface UserDAO {

    @NotNull
    Profile update(@NotNull Profile profile);

    @NotNull
    WishList update(@NotNull WishList wishList);

    @Nullable
    User findUser(@Nullable String identifier);

    @Nullable
    WishList findWishList(@NotNull User owner, @NotNull String name);

    @NotNull
    Profile getProfile(@NotNull User user);

    @NotNull
    SizedIterator<WishList> getWishLists(@NotNull User user);
}
