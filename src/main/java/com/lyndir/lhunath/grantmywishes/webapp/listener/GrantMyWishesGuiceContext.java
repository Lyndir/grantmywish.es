/*
 *   Copyright 2010, Maarten Billemont
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.lyndir.lhunath.grantmywishes.webapp.listener;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.inject.*;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.lyndir.lhunath.grantmywishes.data.DAOModule;
import com.lyndir.lhunath.grantmywishes.model.ServiceModule;
import com.lyndir.lhunath.grantmywishes.webapp.GrantMyWishesWebApplication;
import com.lyndir.lhunath.opal.wayward.servlet.DisableURLSessionFilter;
import javax.servlet.ServletContext;
import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.*;


/**
 * <h2>{@link GrantMyWishesGuiceContext}<br> <sub>[in short] (TODO).</sub></h2>
 *
 * <p> <i>Jan 11, 2010</i> </p>
 *
 * @author lhunath
 */
public class GrantMyWishesGuiceContext extends GuiceServletContextListener {

    private static final String PATH_WICKET = "/*";

    static final Key<WicketFilter> wicketFilter = Key.get( WicketFilter.class );

    /**
     * {@inheritDoc}
     */
    @Override
    protected Injector getInjector() {

        return Guice.createInjector(
                Stage.DEVELOPMENT, new DAOModule(), new ServiceModule(), new ServletModule() {

            @Override
            protected void configureServlets() {

                Builder<String, String> paramBuilder;

                // Disable jsessionid in URLs.
                filter( "/*" ).through( DisableURLSessionFilter.class );
                bind( DisableURLSessionFilter.class ).in( Scopes.SINGLETON );

                // Wicket
                paramBuilder = new ImmutableMap.Builder<String, String>();
                paramBuilder.put( WicketFilter.APP_FACT_PARAM, ContextParamWebApplicationFactory.class.getCanonicalName() );
                paramBuilder.put(
                        ContextParamWebApplicationFactory.APP_CLASS_PARAM, GrantMyWishesWebApplication.class.getCanonicalName() );
                paramBuilder.put( WicketFilter.FILTER_MAPPING_PARAM, PATH_WICKET );
                paramBuilder.put( Application.CONFIGURATION, Application.DEVELOPMENT );

                filter( PATH_WICKET ).through( wicketFilter, paramBuilder.build() );
                bind( WicketFilter.class ).in( Scopes.SINGLETON );
            }
        } );
    }

    /**
     * @param servletContext The request's servlet context.
     *
     * @return The Guice {@link Injector} that was added to the given {@link ServletContext} on initialization.
     */
    public static Injector get(final ServletContext servletContext) {

        return (Injector) servletContext.getAttribute( Injector.class.getName() );
    }

    /**
     * @return The Guice {@link Injector} that was created for the {@link WebApplication} this thread is working with.
     *
     * @see Application#get()
     */
    public static Injector get() {

        return get( ((WebApplication) Application.get( wicketFilter.toString() )).getServletContext() );
    }
}
