package com.lyndir.lhunath.grantmywishes.security;

import com.lyndir.lhunath.grantmywishes.data.User;
import com.lyndir.lhunath.opal.security.SecurityToken;


/**
 * <i>07 07, 2011</i>
 *
 * @author lhunath
 */
public class GSecurityToken extends SecurityToken<User> {

    /**
     * Use this token <b>ONLY</b> for requests that the subject can't gain anything from. The result must not be given or hinted at to the
     * subject.
     */
    // TODO: Should this be moved into SecurityServiceImpl and made private?
    public static final GSecurityToken INTERNAL_USE_ONLY = new GSecurityToken( null ) {

        @Override
        public boolean isInternalUseOnly() {

            return true;
        }
    };

    /**
     * @param actor The subject that has requested or will gain access to the result of the operation.
     */
    public GSecurityToken(final User actor) {

        super( actor );
    }
}
