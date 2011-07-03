package com.lyndir.lhunath.grantmywishes.data.service;

import com.lyndir.lhunath.grantmywishes.data.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public interface UserDAO {

    @NotNull
    User update(@NotNull User user);

    @Nullable
    User findUser(@Nullable String identifier);
}
