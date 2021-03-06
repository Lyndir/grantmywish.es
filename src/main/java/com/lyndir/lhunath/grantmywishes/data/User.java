package com.lyndir.lhunath.grantmywishes.data;

import com.lyndir.lhunath.opal.security.Subject;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import com.lyndir.lhunath.opal.system.util.MetaObject;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class User extends MetaObject implements Subject {

    static final transient Messages msgs = MessagesFactory.create( Messages.class );

    private String name;

    public User(final String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    @Override
    public String getLocalizedType() {

        return msgs.type();
    }

    @Override
    public String getLocalizedInstance() {

        return msgs.instance( getName() );
    }

    interface Messages {

        String type();

        String instance(String name);
    }
}
