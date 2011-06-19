package com.lyndir.lhunath.grantmywishes.webapp.page;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import org.apache.wicket.behavior.StringHeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.*;
import org.apache.wicket.util.template.JavaScriptTemplate;
import org.apache.wicket.util.template.PackagedTextTemplate;


/**
 * <h2>{@link LayoutPage}<br> <sub>[in short] (TODO).</sub></h2>
 *
 * <p> <i>05 02, 2010</i> </p>
 *
 * @author lhunath
 */
public class LayoutPage extends WebPage {

    private IModel<?>                                     pageTitle = Model.of( "grantmywish.es" );
    private IModel<? extends List<? extends SectionInfo>> sections  = new LoadableDetachableModel<List<? extends SectionInfo>>() {
        @Override
        protected List<? extends SectionInfo> load() {

            return ImmutableList.of( new SectionInfo( id ) );
        }
    };

    @Override
    protected void onInitialize() {

        super.onInitialize();

        add( new Label( "pageTitle", pageTitle ) );
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

        add(
                new ListView<SectionInfo>( "navMenu", sections ) {
                    @Override
                    protected void populateItem(final ListItem<SectionInfo> sectionInfoListItem) {

                        sectionInfoListItem.add(
                                new ExternalLink(
                                        "toolItem", String.format( "#%s", sectionInfoListItem.getModelObject().getId() ) ) );
                        sectionInfoListItem.add( sectionInfoListItem.getModelObject().getToolPanel( "toolPanel" ) );
                    }
                } );
    }
}
