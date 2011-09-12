package com.lyndir.lhunath.grantmywishes.model.service;

import com.google.common.base.Predicate;
import com.lyndir.lhunath.grantmywishes.data.Wish;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import org.jetbrains.annotations.NotNull;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public interface WishService {

    @NotNull
    Wish update(@NotNull Wish wish);

    SizedIterator<Wish> getWishes(@NotNull Predicate<Wish> predicate);

    @NotNull
    Wish getWish(String wishName);
}
