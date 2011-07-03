package com.lyndir.lhunath.grantmywishes.error;

import com.lyndir.lhunath.opal.wayward.i18n.MessagesFactory;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class NoSuchUserException extends Exception {

    static final Messages msgs = MessagesFactory.create( Messages.class );

    private final String identifier;

    /**
     * @param identifier The userName that is no longer available.
     */
    public NoSuchUserException(final String identifier) {

        this.identifier = identifier;
    }

    /**
     * @return The userName that is no longer available.
     */
    public String getIdentifier() {

        return identifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {

        return String.format( "No user found with identifier: %s.", identifier );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocalizedMessage() {

        return msgs.message( identifier );
    }

    interface Messages {

        String message(String userName);
    }
}
