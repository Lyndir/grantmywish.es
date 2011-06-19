package com.lyndir.lhunath.grantmywishes.webapp;

import com.lyndir.lhunath.grantmywishes.webapp.listener.GrantMyWishesGuiceContext;
import com.lyndir.lhunath.grantmywishes.webapp.page.LayoutPage;
import org.apache.wicket.Page;
import org.apache.wicket.guice.InjectionFlagCachingGuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IExceptionSettings;


/**
 * <h2>{@link GrantMyWishesWebApplication}<br> <sub>[in short] (TODO).</sub></h2>
 *
 * <p> <i>05 02, 2010</i> </p>
 *
 * @author lhunath
 */
public class GrantMyWishesWebApplication extends WebApplication {

    @Override
    protected void init() {

        getExceptionSettings().setUnexpectedExceptionDisplay( IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE );

        addComponentInstantiationListener( new InjectionFlagCachingGuiceComponentInjector( this, GrantMyWishesGuiceContext.get() ) );
    }

    @Override
    public Class<? extends Page> getHomePage() {

        return LayoutPage.class;
    }

    public static GrantMyWishesWebApplication get() {

        return (GrantMyWishesWebApplication) WebApplication.get();
    }
}
