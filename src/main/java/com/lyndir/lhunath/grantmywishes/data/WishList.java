package com.lyndir.lhunath.grantmywishes.data;

import com.google.common.collect.Lists;
import com.lyndir.lhunath.opal.system.util.MetaObject;
import java.io.Serializable;
import java.util.List;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class WishList extends MetaObject implements Serializable {

    private String name;

    private final List<Wish> wishes = Lists.newLinkedList();

    public WishList(final String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public List<Wish> getWishes() {

        return wishes;
    }
}
