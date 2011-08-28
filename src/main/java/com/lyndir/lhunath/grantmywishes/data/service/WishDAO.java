package com.lyndir.lhunath.grantmywishes.data.service;

import com.google.common.base.Predicate;
import com.lyndir.lhunath.grantmywishes.data.*;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import org.jetbrains.annotations.NotNull;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public interface WishDAO {

    @NotNull
    Wish update(@NotNull Wish wish);

    @NotNull
    SizedIterator<Wish> getWishes(@NotNull Predicate<Wish> predicate);
}
