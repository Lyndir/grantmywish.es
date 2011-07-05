package com.lyndir.lhunath.grantmywishes.data;

import com.lyndir.lhunath.opal.system.util.MetaObject;


/**
 * <i>07 05, 2011</i>
 *
 * @author lhunath
 */
public class ProfileItem extends MetaObject {

    private final ProfileItemType type;
    private String value;

    public ProfileItem(final ProfileItemType type) {

        this.type = type;
    }

    public ProfileItemType getType() {

        return type;
    }

    public String getValue() {

        return value;
    }

    public void setValue(final String value) {

        this.value = value;
    }
}
