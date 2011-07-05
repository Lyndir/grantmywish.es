package com.lyndir.lhunath.grantmywishes.webapp;

import com.lyndir.lhunath.grantmywishes.webapp.listener.GrantMyWishesGuiceContext;
import com.lyndir.lhunath.grantmywishes.webapp.page.LayoutPage;
import com.lyndir.lhunath.grantmywishes.webapp.section.SectionNavigationController;
import com.lyndir.lhunath.opal.wayward.i18n.Localized;
import com.lyndir.lhunath.opal.wayward.i18n.LocalizedConverter;
import com.lyndir.lhunath.opal.wayward.js.AjaxHooks;
import com.lyndir.lhunath.opal.wayward.navigation.TabAjaxRequestListener;
import org.apache.wicket.*;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.guice.InjectionFlagCachingGuiceComponentInjector;
import org.apache.wicket.markup.*;
import org.apache.wicket.markup.parser.filter.ExtendedOpenCloseTagExpander;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.util.convert.ConverterLocator;


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

                        MarkupParser markupParser = super.newMarkupParser( resource );
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

    @Override
    public Session newSession(final Request request, final Response response) {

        return new GrantMyWishesSession( request );
    }

    @Override
    protected IConverterLocator newConverterLocator() {

        ConverterLocator converterLocator = new ConverterLocator();
        converterLocator.set( Localized.class, new LocalizedConverter() );

        return converterLocator;
    }

    @Override
    public AjaxRequestTarget newAjaxRequestTarget(final Page page) {

        AjaxRequestTarget target = super.newAjaxRequestTarget( page );
        AjaxHooks.installAjaxEvents( target );

        target.addListener( TabAjaxRequestListener.of( SectionNavigationController.get() ) );
        //        if (page instanceof LayoutPage)
        //            ((LayoutPage) page).addMessages

        return target;
    }

    public static GrantMyWishesWebApplication get() {

        return (GrantMyWishesWebApplication) WebApplication.get();
    }
}
