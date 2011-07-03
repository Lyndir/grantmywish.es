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
public class User extends MetaObject implements Serializable {

    private String name;
    private String email;

    private final List<WishList> wishLists = Lists.newLinkedList();

    public User(final String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(final String email) {

        this.email = email;
    }

    public List<WishList> getWishLists() {

        return wishLists;
    }
}
