package com.lyndir.lhunath.grantmywishes.data;

import com.google.common.collect.Lists;
import com.lyndir.lhunath.grantmywishes.security.GGlobalSecureObject;
import com.lyndir.lhunath.opal.security.*;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import java.util.ArrayList;
import java.util.List;


/**
 * <i>07 05, 2011</i>
 *
 * @author lhunath
 */
public class Profile extends AbstractSecureObject<User, GlobalSecureObject<User>> {

    static final Messages msgs = MessagesFactory.create( Messages.class );

    private final ArrayList<ProfileItem> profileItems = Lists.newArrayListWithExpectedSize( ProfileItemType.values().length );

    public Profile(final User user) {

        super( user );

        for (final ProfileItemType profileItemType : ProfileItemType.values())
            profileItems.add( new ProfileItem( profileItemType ) );
    }

    public List<ProfileItem> getProfileItems() {

        return profileItems;
    }

    @Override
    public GlobalSecureObject<User> getParent() {

        return GGlobalSecureObject.DEFAULT;
    }

    @Override
    public String getLocalizedType() {

        return msgs.type();
    }

    @Override
    public String getLocalizedInstance() {

        return msgs.instance( getOwner() );
    }

    interface Messages {

        String type();

        String instance(Subject owner);
    }
}
