package com.lyndir.lhunath.grantmywishes.webapp.page;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.User;
import com.lyndir.lhunath.grantmywishes.error.NoSuchUserException;
import com.lyndir.lhunath.grantmywishes.error.UserNameUnavailableException;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.grantmywishes.webapp.GrantMyWishesSession;
import com.lyndir.lhunath.grantmywishes.webapp.section.*;
import com.lyndir.lhunath.opal.system.logging.Logger;
import com.lyndir.lhunath.opal.wayward.behavior.CSSClassAttributeAppender;
import com.lyndir.lhunath.opal.wayward.js.AjaxHooks;
import com.lyndir.lhunath.opal.wayward.navigation.NavigationPageListener;
import java.util.Map;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.*;


/**
 * <h2>{@link LayoutPage}<br> <sub>[in short] (TODO).</sub></h2>
 *
 * <p> <i>05 02, 2010</i> </p>
 *
 * @author lhunath
 */
public class LayoutPage extends WebPage {

    static final Logger logger = Logger.get( LayoutPage.class );

    private final IModel<?>                        pageTitle       = Model.of( "grantmywish.es" );
    private final Map<SectionInfo, SectionTool<?>> sectionTools    = Maps.newHashMapWithExpectedSize( SectionInfo.values().size() );
    private final Map<SectionInfo, SectionContent> sectionContents = Maps.newHashMapWithExpectedSize( SectionInfo.values().size() );

    @Inject
    UserService userService;

    @Override
    protected void onInitialize() {

        super.onInitialize();

        AjaxHooks.installAjaxEvents( this );
        AjaxHooks.installPageEvents( this, NavigationPageListener.of( SectionNavigationController.get() ) );

        for (final SectionInfo sectionInfo : SectionInfo.values())
            sectionTools.put( sectionInfo, sectionInfo.getToolPanel( "toolPanel" ) );
        for (final SectionInfo sectionInfo : SectionInfo.values())
            sectionContents.put( sectionInfo, sectionInfo.getContentPanel( "contentPanel" ) );

        add( new Label( "pageTitle", pageTitle ) );
        //        add(
        //                new StringHeaderContributor(
        //                        new LoadableDetachableModel<String>() {
        //                            @Override
        //                            protected String load() {
        //
        //                                return new JavaScriptTemplate( new PackagedTextTemplate( LayoutPage.class, "trackPage.js" ) ).asString(
        //                                        ImmutableMap.<String, Object>builder() //
        //                                                .put( "pageView", getPageClass().getSimpleName() ).build() );
        //                            }
        //                        } ) );
        add(
                new WebMarkupContainer( "user" ) {
                    boolean askUser;

                    @Override
                    protected void onInitialize() {

                        super.onInitialize();

                        final WebMarkupContainer userContainer = this;
                        add(
                                new AjaxLink<Void>( "loggedOut" ) {
                                    @Override
                                    public void onClick(final AjaxRequestTarget target) {

                                        askUser = true;
                                        target.addComponent( userContainer );
                                    }

                                    @Override
                                    protected void onConfigure() {

                                        super.onConfigure();

                                        setVisible( GrantMyWishesSession.get().getUser() == null && !askUser );
                                    }
                                } );
                        add(
                                new Form<Void>( "logIn" ) {
                                    public IModel<String> identifier = Model.of();

                                    @Override
                                    protected void onInitialize() {

                                        super.onInitialize();

                                        add( new TextField<String>( "identifier", identifier ) );
                                    }

                                    @Override
                                    protected void onConfigure() {

                                        super.onConfigure();

                                        setVisible( GrantMyWishesSession.get().getUser() == null && askUser );
                                    }

                                    @Override
                                    protected void onSubmit() {

                                        User user;
                                        try {
                                            user = userService.authenticate( identifier.getObject() );
                                        }
                                        catch (NoSuchUserException ignored) {
                                            // TODO: Should be a separate process
                                            try {
                                                user = userService.newUser( identifier.getObject() );
                                            }
                                            catch (UserNameUnavailableException ee) {
                                                error( ee.getLocalizedMessage() );
                                                return;
                                            }
                                        }

                                        GrantMyWishesSession.get().setUser( user );
                                        askUser = false;
                                    }
                                } );
                        add(
                                new Link<Void>(
                                        "loggedIn" ) {
                                    @Override
                                    public void onClick() {

                                        GrantMyWishesSession.get().setUser( null );
                                    }

                                    @Override
                                    protected void onInitialize() {

                                        super.onInitialize();

                                        add(
                                                new Label(
                                                        "user", new LoadableDetachableModel<String>() {
                                                    @Override
                                                    protected String load() {

                                                        return checkNotNull( GrantMyWishesSession.get().getUser() ).getName();
                                                    }
                                                } ) );
                                    }

                                    @Override
                                    protected void onConfigure() {

                                        super.onConfigure();

                                        setVisible( GrantMyWishesSession.get().getUser() != null );
                                    }
                                } );
                    }
                }.setOutputMarkupId( true ) );
        add(
                new ListView<SectionInfo>( "navMenu", ImmutableList.copyOf( SectionInfo.values() ) ) {
                    @Override
                    protected void populateItem(final ListItem<SectionInfo> sectionInfoListItem) {

                        final SectionInfo sectionInfo = sectionInfoListItem.getModelObject();

                        sectionInfoListItem.add(
                                new AjaxLink<Void>( "toolItem" ) {
                                    @Override
                                    public void onClick(final AjaxRequestTarget target) {

                                        SectionNavigationController.get().activateNewTab( sectionInfo );
                                    }
                                }.add( new CSSClassAttributeAppender( sectionInfo.getToolItemSprite() ) ) );
                        sectionInfoListItem.add( sectionTools.get( sectionInfoListItem.getModelObject() ) );
                    }
                } );
        add(
                new ListView<SectionInfo>( "sections", ImmutableList.copyOf( SectionInfo.values() ) ) {
                    @Override
                    protected void populateItem(final ListItem<SectionInfo> sectionInfoListItem) {

                        sectionInfoListItem.add( sectionContents.get( sectionInfoListItem.getModelObject() ) );
                    }
                } );
    }

    @SuppressWarnings({ "unchecked" })
    public <C extends SectionContent> C getContent(final SectionTool<C> sectionTool) {

        for (final Map.Entry<SectionInfo, SectionTool<?>> sectionToolEntry : sectionTools.entrySet())
            if (sectionToolEntry.getValue() == sectionTool)
                return (C) sectionContents.get( sectionToolEntry.getKey() );

        throw logger.bug( "Tried to look up content in page for section tool: %s, but this tool does not exist in the page.", sectionTool );
    }
}
