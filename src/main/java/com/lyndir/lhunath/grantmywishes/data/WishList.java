package com.lyndir.lhunath.grantmywishes.data;

import com.google.common.collect.Lists;
import com.lyndir.lhunath.opal.security.AbstractSecureObject;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import java.util.List;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class WishList extends AbstractSecureObject<User, Profile> {

    static final transient Messages msgs = MessagesFactory.create( Messages.class );

    private String name;
    private final Profile profile;

    private final List<WishListItem> items = Lists.newLinkedList();

    public WishList(final String name, final Profile profile) {

        this.name = name;
        this.profile = profile;
    }

    public String getName() {

        return name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public List<WishListItem> getItems() {

        return items;
    }

    @Override
    public Profile getParent() {

        return profile;
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
