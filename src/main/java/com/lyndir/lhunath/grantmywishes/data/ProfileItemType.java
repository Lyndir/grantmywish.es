package com.lyndir.lhunath.grantmywishes.data;

import com.lyndir.lhunath.opal.system.i18n.*;


/**
 * <i>07 05, 2011</i>
 *
 * @author lhunath
 */
public enum ProfileItemType implements Localized {

    GIVEN_NAME,
    FAMILY_NAME;

    static final transient Messages msgs = MessagesFactory.create( Messages.class );

    @Override
    public String getLocalizedType() {

        return msgs.type();
    }

    @Override
    public String getLocalizedInstance() {

        return msgs.instance(this);
    }

    interface Messages {

        String type();

        String instance(@KeyAppender ProfileItemType profileItemType);
    }
}
