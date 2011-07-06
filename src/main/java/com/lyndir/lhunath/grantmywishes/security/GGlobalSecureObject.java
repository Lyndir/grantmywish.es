package com.lyndir.lhunath.grantmywishes.security;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.lyndir.lhunath.grantmywishes.data.User;
import com.lyndir.lhunath.grantmywishes.webapp.listener.GrantMyWishesGuiceContext;
import com.lyndir.lhunath.opal.security.*;


/**
 * <i>07 07, 2011</i>
 *
 * @author lhunath
 */
public class GGlobalSecureObject extends GlobalSecureObject<User> {

    /**
     * The default {@link SecureObject} that all top-level objects should use as their parent.
     */
    public static final transient GGlobalSecureObject DEFAULT;

    static {
        ObjectContainer db = GrantMyWishesGuiceContext.get().getInstance( ObjectContainer.class );
        ObjectSet<GGlobalSecureObject> firstQuery = db.query( GGlobalSecureObject.class );

        if (firstQuery.hasNext())
            DEFAULT = firstQuery.next();

        else
            DEFAULT = new GGlobalSecureObject();

        DEFAULT.getACL().setDefaultPermission( Permission.NONE );
        db.store( DEFAULT );
    }
}
