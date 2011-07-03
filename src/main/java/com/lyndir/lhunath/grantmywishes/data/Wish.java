package com.lyndir.lhunath.grantmywishes.data;

import com.lyndir.lhunath.opal.system.util.MetaObject;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class Wish extends MetaObject {

    private int quantity;
    private Item item;

    public Wish(final Item item) {

        this.item = item;
    }

    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(final int quantity) {

        this.quantity = quantity;
    }

    public Item getItem() {

        return item;
    }

    public void setItem(final Item item) {

        this.item = item;
    }
}
