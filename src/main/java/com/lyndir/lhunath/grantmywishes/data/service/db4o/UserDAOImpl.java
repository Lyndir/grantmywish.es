package com.lyndir.lhunath.grantmywishes.data.service.db4o;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.*;
import com.lyndir.lhunath.grantmywishes.data.service.UserDAO;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import com.lyndir.lhunath.opal.system.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class UserDAOImpl implements UserDAO {

    private final ObjectContainer db;

    @Inject
    public UserDAOImpl(final ObjectContainer db) {

        this.db = db;
    }

    @NotNull
    @Override
    public Profile update(@NotNull final Profile profile) {

        db.store( profile );

        return profile;
    }

    @NotNull
    @Override
    public WishList update(@NotNull final WishList wishList) {

        db.store( wishList );

        return wishList;
    }

    @Override
    public User findUser(@Nullable final String identifier) {

        ObjectSet<User> results = db.query(
                new Predicate<User>() {

                    @Override
                    public boolean match(final User candidate) {

                        return ObjectUtils.isEqual( candidate.getName(), identifier ) //
                               || ObjectUtils.isEqual( candidate.getEmail(), identifier );
                    }
                } );

        return Iterables.getOnlyElement( results, null );
    }

    @Override
    public WishList findWishList(@NotNull final User owner, @NotNull final String name) {

        ObjectSet<WishList> results = db.query(
                new Predicate<WishList>() {

                    @Override
                    public boolean match(final WishList candidate) {

                        return ObjectUtils.isEqual( candidate.getOwner(), owner ) //
                               || ObjectUtils.isEqual( candidate.getName(), name );
                    }
                } );

        return Iterables.getOnlyElement( results, null );
    }

    @NotNull
    @Override
    public Profile getProfile(@NotNull final User user) {

        ObjectSet<Profile> results = db.query(
                new Predicate<Profile>() {

                    @Override
                    public boolean match(final Profile candidate) {

                        return ObjectUtils.isEqual( user, candidate.getOwner() );
                    }
                } );

        return Iterables.getOnlyElement( results );
    }

    @NotNull
    @Override
    public SizedIterator<WishList> getWishLists(@NotNull final User user) {

        ObjectSet<WishList> results = db.query(
                new Predicate<WishList>() {

                    @Override
                    public boolean match(final WishList candidate) {

                        return ObjectUtils.isEqual( user, candidate.getOwner() );
                    }
                } );

        return SizedIterator.of( results );
    }
}
