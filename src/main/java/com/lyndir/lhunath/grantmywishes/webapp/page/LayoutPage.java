package com.lyndir.lhunath.grantmywishes.webapp.page;

import com.google.common.collect.ImmutableMap;
import com.lyndir.lhunath.grantmywishes.webapp.section.SectionInfo;
import com.lyndir.lhunath.grantmywishes.webapp.section.SectionNavigationController;
import com.lyndir.lhunath.opal.wayward.behavior.CSSClassAttributeAppender;
import com.lyndir.lhunath.opal.wayward.js.AjaxHooks;
import com.lyndir.lhunath.opal.wayward.navigation.FragmentNavigationListener;
import java.util.List;
import org.apache.wicket.behavior.StringHeaderContributor;
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

    private final IModel<?>                                 pageTitle = Model.of( "grantmywish.es" );
    private final IModel<? extends List<SectionInfo<?, ?>>> sections  = new LoadableDetachableModel<List<SectionInfo<?, ?>>>() {
        @Override
        protected List<SectionInfo<?, ?>> load() {

            return SectionInfo.sections;
        }
    };

    @Override
    protected void onInitialize() {

        super.onInitialize();

        AjaxHooks.installAjaxEvents( this );
        AjaxHooks.installPageEvents( this, FragmentNavigationListener.PageListener.of( SectionNavigationController.get() ) );

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
                new ListView<SectionInfo<?, ?>>( "navMenu", sections ) {
                    @Override
                    protected void populateItem(final ListItem<SectionInfo<?, ?>> sectionInfoListItem) {

                        SectionInfo<?, ?> sectionInfo = sectionInfoListItem.getModelObject();

                        sectionInfoListItem.add(
                                new ExternalLink( "toolItem", String.format( "#%s", sectionInfo.getId() ) ).add(
                                        new CSSClassAttributeAppender( sectionInfo.getToolItemSprite() ) ) );
                        sectionInfoListItem.add( sectionInfo.getToolPanel( "toolPanel" ) );
                    }
                } );
        add(
                new ListView<SectionInfo<?, ?>>( "sections", sections ) {
                    @Override
                    protected void populateItem(final ListItem<SectionInfo<?, ?>> sectionInfoListItem) {

                        SectionInfo<?, ?> sectionInfo = sectionInfoListItem.getModelObject();

                        sectionInfoListItem.add( sectionInfo.getContentPanel( "contentPanel" ) );
                    }
                } );
    }
}
