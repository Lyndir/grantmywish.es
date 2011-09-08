package com.lyndir.lhunath.grantmywishes.data;

import com.google.common.collect.Maps;
import com.lyndir.lhunath.grantmywishes.security.GGlobalSecureObject;
import com.lyndir.lhunath.opal.security.*;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import java.util.Map;


/**
 * <i>07 05, 2011</i>
 *
 * @author lhunath
 */
public class Profile extends AbstractSecureObject<User, GlobalSecureObject<User>> {

    static final transient Messages msgs = MessagesFactory.create( Messages.class );

    private final Map<ProfileItemType, ProfileItem> profileItems = Maps.newTreeMap();

    public Profile(final User user) {

        super( user );

        for (final ProfileItemType profileItemType : ProfileItemType.values())
            profileItems.put( profileItemType, new ProfileItem( profileItemType ) );
    }

    public Map<ProfileItemType, ProfileItem> getProfileItems() {

        return profileItems;
    }

    public String getProfileValue(final ProfileItemType profileItemType) {

        return profileItems.get( profileItemType ).getValue();
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
