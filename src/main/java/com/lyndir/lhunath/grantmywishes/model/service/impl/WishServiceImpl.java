package com.lyndir.lhunath.grantmywishes.model.service.impl;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.Wish;
import com.lyndir.lhunath.grantmywishes.data.service.WishDAO;
import com.lyndir.lhunath.grantmywishes.model.service.WishService;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import org.jetbrains.annotations.NotNull;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class WishServiceImpl implements WishService {

    private final WishDAO wishDAO;

    @Inject
    public WishServiceImpl(final WishDAO wishDAO) {

        this.wishDAO = wishDAO;
    }

    @NotNull
    @Override
    public Wish newWish(@NotNull final String name) {

        return wishDAO.update( new Wish( name ) );
    }

    @Override
    public SizedIterator<Wish> getWishes(@NotNull final Predicate<Wish> predicate) {

        return wishDAO.getWishes( predicate );
    }
}