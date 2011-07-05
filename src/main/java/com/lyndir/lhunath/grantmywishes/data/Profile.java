package com.lyndir.lhunath.grantmywishes.data;

import com.google.common.collect.Lists;
import com.lyndir.lhunath.opal.system.util.MetaObject;
import java.util.List;


/**
 * <i>07 05, 2011</i>
 *
 * @author lhunath
 */
public class Profile extends MetaObject {

    private final List<ProfileItem> profileItems = Lists.newArrayListWithExpectedSize( ProfileItemType.values().length );

    public Profile() {

        for (final ProfileItemType profileItemType : ProfileItemType.values())
            profileItems.add( new ProfileItem( profileItemType ) );
    }

    public List<ProfileItem> getProfileItems() {

        return profileItems;
    }
}
