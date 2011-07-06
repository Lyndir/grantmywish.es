package com.lyndir.lhunath.grantmywishes.security;

import com.lyndir.lhunath.grantmywishes.data.User;
import com.lyndir.lhunath.opal.security.AbstractSecureObject;
import org.jetbrains.annotations.NotNull;


/**
 * <i>07 07, 2011</i>
 *
 * @author lhunath
 */
public abstract class GSecureObject<P extends GSecureObject<?>> extends AbstractSecureObject<User, P> {

    protected GSecureObject() {

    }

    protected GSecureObject(@NotNull final User owner) {

        super( owner );
    }
}
