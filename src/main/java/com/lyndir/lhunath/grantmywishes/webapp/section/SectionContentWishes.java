package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.WishList;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.grantmywishes.model.service.WishService;
import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import java.util.Deque;
import java.util.List;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentWishes extends SectionContent {

    @Inject
    UserService userService;

    @Inject
    WishService wishService;

    WishList wishList;

    public SectionContentWishes(final String id) {

        super( id );
    }

    void setWishList(final String ownerName, final String wishListName) {

        wishList = userService.getWishList( userService.getUser( ownerName ), wishListName );
    }

    public static class SectionStateWishes extends SectionState<SectionContentWishes> {

        public SectionStateWishes(final String fragment) {

            super( fragment );
        }

        public SectionStateWishes(final SectionContentWishes panel) {

            super( panel );
        }

        public SectionStateWishes(final WishList wishList) {

            super( ImmutableList.of( "wishlist", wishList.getOwner().getName(), wishList.getName() ) );
        }

        @Override
        protected List<String> loadFragments(final SectionContentWishes panel) {

            if (panel.wishList != null)
                return ImmutableList.of("wishlist", panel.wishList.getOwner().getName(), panel.wishList.getName());

            return ImmutableList.of();
        }

        @Override
        protected void applyFragments(final SectionContentWishes panel, final Deque<String> fragments)
                throws IncompatibleStateException {

            if (fragments.isEmpty())
                return;

            String type = fragments.pop();
            if ("wishlist".equals( type ))
                panel.setWishList( fragments.pop(), fragments.pop() );
            else
                throw new IncompatibleStateException();
        }
    }
}
