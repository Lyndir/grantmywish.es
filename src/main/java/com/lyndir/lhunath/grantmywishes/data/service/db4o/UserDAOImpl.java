package com.lyndir.lhunath.grantmywishes.data.service.db4o;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.User;
import com.lyndir.lhunath.grantmywishes.data.service.UserDAO;
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
    public User update(@NotNull final User user) {

        db.store( user );

        return user;
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
}
