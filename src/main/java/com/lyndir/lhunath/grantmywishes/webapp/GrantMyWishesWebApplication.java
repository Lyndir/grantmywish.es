package com.lyndir.lhunath.grantmywishes.webapp;

import com.lyndir.lhunath.grantmywishes.webapp.listener.GrantMyWishesGuiceContext;
import com.lyndir.lhunath.grantmywishes.webapp.page.LayoutPage;
import org.apache.wicket.Page;
import org.apache.wicket.guice.InjectionFlagCachingGuiceComponentInjector;
import org.apache.wicket.markup.*;
import org.apache.wicket.markup.parser.filter.ExtendedOpenCloseTagExpander;
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

        getMarkupSettings().setMarkupParserFactory(
                new MarkupParserFactory() {

                    @Override
                    public MarkupParser newMarkupParser(final MarkupResourceStream resource) {

                        MarkupParser markupParser = super.newMarkupParser(resource);
                        markupParser.appendMarkupFilter( new ExtendedOpenCloseTagExpander() );

                        return markupParser;
                    }
                } );
        getMarkupSettings().setDefaultMarkupEncoding( "UTF-8" );

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
