package com.lyndir.lhunath.grantmywishes.error;

import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;


/**
 * <i>07 05, 2011</i>
 *
 * @author lhunath
 */
public class UserNameUnavailableException extends Exception {

    static final Messages msgs = MessagesFactory.create( Messages.class );

    private final String userName;

    /**
     * @param userName The userName that is no longer available.
     */
    public UserNameUnavailableException(final String userName) {

        this.userName = userName;
    }

    /**
     * @return The userName that is no longer available.
     */
    public String getUserName() {

        return userName;
    }

    @Override
    public String getMessage() {

        return String.format( "User name unavailable: %s.", userName );
    }

    @Override
    public String getLocalizedMessage() {

        return msgs.message( userName );
    }

    interface Messages {

        String message(String userName);
    }
}
