package com.lyndir.lhunath.grantmywishes.data;

/**
 * <i>09 10, 2011</i>
 *
 * @author lhunath
 */
public enum WishGroup {

    FRIENDS,
    HOT,
    RECOMMENDATIONS;

    public boolean apply(final Wish wish, final User user) {

        // TODO
        return true;
    }
}
