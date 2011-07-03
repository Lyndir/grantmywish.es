package com.lyndir.lhunath.grantmywishes.data;

import java.net.URL;


/**
 * <i>07 03, 2011</i>
 *
 * @author lhunath
 */
public class Item {

    private final String name;
    private       String description;
    private       URL    store;

    public Item(final String name) {

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
}
