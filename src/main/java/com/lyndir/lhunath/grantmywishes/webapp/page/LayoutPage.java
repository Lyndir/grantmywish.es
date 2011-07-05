package com.lyndir.lhunath.grantmywishes.webapp.page;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.User;
import com.lyndir.lhunath.grantmywishes.error.NoSuchUserException;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.grantmywishes.webapp.GrantMyWishesSession;
import com.lyndir.lhunath.grantmywishes.webapp.section.SectionInfo;
import com.lyndir.lhunath.grantmywishes.webapp.section.SectionNavigationController;
import com.lyndir.lhunath.opal.wayward.behavior.CSSClassAttributeAppender;
import com.lyndir.lhunath.opal.wayward.js.AjaxHooks;
import com.lyndir.lhunath.opal.wayward.navigation.TabPageListener;
import java.util.List;
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

    private final IModel<?>                           pageTitle = Model.of( "grantmywish.es" );
    private final IModel<? extends List<SectionInfo>> sections  = new LoadableDetachableModel<List<SectionInfo>>() {
        @Override
        protected List<SectionInfo> load() {

            return ImmutableList.copyOf( SectionInfo.values() );
        }
    };

    @Inject
    UserService userService;

    @Override
    protected void onInitialize() {

        super.onInitialize();

        AjaxHooks.installAjaxEvents( this );
        AjaxHooks.installPageEvents( this, TabPageListener.of( SectionNavigationController.get() ) );

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
                                        catch (NoSuchUserException e) {
                                            error( e.getLocalizedMessage() );
                                            // TODO: Should be a separate process
                                            user = userService.newUser( identifier.getObject() );
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

                                                        return GrantMyWishesSession.get().getUser().getName();
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
                new ListView<SectionInfo>( "navMenu", sections ) {
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
                        sectionInfoListItem.add( sectionInfo.getToolPanel( "toolPanel" ) );
                    }
                } );
        add(
                new ListView<SectionInfo>( "sections", sections ) {
                    @Override
                    protected void populateItem(final ListItem<SectionInfo> sectionInfoListItem) {

                        SectionInfo sectionInfo = sectionInfoListItem.getModelObject();

                        sectionInfoListItem.add( sectionInfo.getContentPanel( "contentPanel" ) );
                    }
                } );
    }
}
