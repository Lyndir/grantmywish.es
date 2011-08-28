package com.lyndir.lhunath.grantmywishes.data;

import com.lyndir.lhunath.opal.system.util.MetaObject;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class WishListItem extends MetaObject {

    private int quantity;
    private Wish wish;

    public WishListItem(final Wish wish) {

        this.wish = wish;
    }

    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(final int quantity) {

        this.quantity = quantity;
    }

    public Wish getWish() {

        return wish;
    }

    public void setWish(final Wish wish) {

        this.wish = wish;
    }
}
