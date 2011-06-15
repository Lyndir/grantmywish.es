package com.lyndir.lhunath.grantmywishes.webapp.page;

import com.google.common.collect.ImmutableMap;
import org.apache.wicket.behavior.StringHeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.template.JavaScriptTemplate;
import org.apache.wicket.util.template.PackagedTextTemplate;


/**
 * <h2>{@link LayoutPage}<br> <sub>[in short] (TODO).</sub></h2>
 *
 * <p> <i>05 02, 2010</i> </p>
 *
 * @author lhunath
 */
public abstract class LayoutPage extends WebPage {

    protected LayoutPage() {

        add( new Label( "pageTitle", getPageTitle() ) );
        add(
                new StringHeaderContributor(
                        new LoadableDetachableModel<String>() {
                            @Override
                            protected String load() {

                                return new JavaScriptTemplate( new PackagedTextTemplate( LayoutPage.class, "trackPage.js" ) ).asString(
                                        ImmutableMap.<String, Object>builder() //
                                                .put( "pageView", getPageClass().getSimpleName() ).build() );
                            }
                        } ) );
    }

    protected abstract IModel<String> getPageTitle();
}
