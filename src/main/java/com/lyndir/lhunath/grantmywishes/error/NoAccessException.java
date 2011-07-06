package com.lyndir.lhunath.grantmywishes.error;

import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;


/**
 * <i>07 05, 2011</i>
 *
 * @author lhunath
 */
public class NoAccessException extends Exception {

    static final Messages msgs = MessagesFactory.create( Messages.class );

    private final String identifier;

    /**
     * @param identifier The userName that is no longer available.
     */
    public NoAccessException(final String identifier) {

        this.identifier = identifier;
    }

    /**
     * @return The identifier that was used to search for a user
     */
    public String getIdentifier() {

        return identifier;
    }

    @Override
    public String getMessage() {

        return String.format( "No user found with identifier: %s.", identifier );
    }

    @Override
    public String getLocalizedMessage() {

        return msgs.message( identifier );
    }

    interface Messages {

        String message(String userName);
    }
}
