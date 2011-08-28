package com.lyndir.lhunath.grantmywishes.data.service.db4o;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.Wish;
import com.lyndir.lhunath.grantmywishes.data.service.WishDAO;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import org.jetbrains.annotations.NotNull;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class WishDAOImpl implements WishDAO {

    private final ObjectContainer db;

    @Inject
    public WishDAOImpl(final ObjectContainer db) {

        this.db = db;
    }

    @NotNull
    @Override
    public Wish update(@NotNull final Wish wish) {

        db.store( wish );

        return wish;
    }

    @NotNull
    @Override
    public SizedIterator<Wish> getWishes(@NotNull final com.google.common.base.Predicate<Wish> predicate) {

        ObjectSet<Wish> results = db.query(
                new Predicate<Wish>() {

                    @Override
                    public boolean match(final Wish candidate) {

                        return predicate.apply( candidate );
                    }
                } );

        return SizedIterator.of( results );
    }
}
