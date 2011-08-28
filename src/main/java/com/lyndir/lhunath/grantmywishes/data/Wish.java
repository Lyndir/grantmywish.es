package com.lyndir.lhunath.grantmywishes.data;

import com.lyndir.lhunath.opal.system.i18n.Localized;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import java.net.URL;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class Wish implements Localized {

    static final transient Messages msgs = MessagesFactory.create( Messages.class );

    private final String name;
    private       String description;
    private       URL    store;

    public Wish(final String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(final String description) {

        this.description = description;
    }

    public URL getStore() {

        return store;
    }

    public void setStore(final URL store) {

        this.store = store;
    }

    @Override
    public String getLocalizedType() {

        return msgs.type();
    }

    @Override
    public String getLocalizedInstance() {

        return msgs.instance( getName() );
    }

    interface Messages {

        String type();

        String instance(String name);
    }
}
