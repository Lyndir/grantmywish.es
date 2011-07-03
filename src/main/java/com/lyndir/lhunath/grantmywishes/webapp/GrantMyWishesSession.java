package com.lyndir.lhunath.grantmywishes.webapp;

import com.lyndir.lhunath.grantmywishes.data.User;
import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;
import org.jetbrains.annotations.Nullable;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class GrantMyWishesSession extends WebSession {

    @Nullable
    private User user;

    public GrantMyWishesSession(final Request request) {

        super( request );
    }

    public static GrantMyWishesSession get() {

        return (GrantMyWishesSession) WebSession.get();
    }

    public void setUser(@Nullable final User user) {

        this.user = user;
    }

    @Nullable
    public User getUser() {

        return user;
    }
}
