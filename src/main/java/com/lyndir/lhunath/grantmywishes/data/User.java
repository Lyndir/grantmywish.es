package com.lyndir.lhunath.grantmywishes.data;

import com.google.common.collect.Lists;
import com.lyndir.lhunath.opal.security.Subject;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import com.lyndir.lhunath.opal.system.util.MetaObject;
import java.util.List;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class User extends MetaObject implements Subject {

    static final Messages msgs = MessagesFactory.create( Messages.class );

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

    @Override
    public String getLocalizedType() {

        return msgs.type();
    }

    @Override
    public String getLocalizedInstance() {

        return msgs.instance( getName(), getEmail() );
    }

    interface Messages {

        String type();

        String instance(String name, String email);
    }
}
