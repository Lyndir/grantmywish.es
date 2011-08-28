package com.lyndir.lhunath.grantmywishes.webapp.section;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.*;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.grantmywishes.model.service.WishService;
import com.lyndir.lhunath.grantmywishes.webapp.GrantMyWishesSession;
import com.lyndir.lhunath.opal.security.error.PermissionDeniedException;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import com.lyndir.lhunath.opal.system.logging.Logger;
import com.lyndir.lhunath.opal.system.util.ObjectUtils;
import com.lyndir.lhunath.opal.wayward.behavior.AjaxSubmitBehavior;
import com.lyndir.lhunath.opal.wayward.behavior.AjaxUpdatingBehaviour;
import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import java.util.Deque;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.*;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentUser extends SectionContent {

    static final Logger logger = Logger.get( SectionContentUser.class );

    static final Messages msgs = MessagesFactory.create( Messages.class );

    @Inject
    UserService userService;

    @Inject
    WishService wishService;

    private String  query;
    private boolean showProfile;
    private boolean showWishes;
    private boolean showWishLists;

    private Component wishLists;

    public SectionContentUser(final String id) {

        super( id );
        select( true, true, true );
    }

    @Override
    protected void onInitialize() {

        super.onInitialize();

        add(
                new Label(
                        "query", new LoadableDetachableModel<String>() {
                    @Override
                    protected String load() {

                        return query;
                    }
                } ) {
                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( query != null && GrantMyWishesSession.get().getUser() != null );
                    }
                } );
        add(
                new WebMarkupContainer( "noQuery" ) {
                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( query == null && GrantMyWishesSession.get().getUser() != null );
                    }
                } );
        add(
                new WebMarkupContainer( "notLoggedIn" ) {
                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( GrantMyWishesSession.get().getUser() == null );
                    }
                } );

        add(
                new WebMarkupContainer( "profile" ) {
                    Profile profile;
                    public ListView<ProfileItem> entry;

                    @Override
                    protected void onInitialize() {

                        super.onInitialize();

                        add(
                                entry = new ListView<ProfileItem>(
                                        "entry", new LoadableDetachableModel<List<? extends ProfileItem>>() {
                                    @Override
                                    protected List<? extends ProfileItem> load() {

                                        try {
                                            return ImmutableList.copyOf(
                                                    (profile = userService.getProfile(
                                                            checkNotNull( GrantMyWishesSession.get().getUser() ) )).getProfileItems() );
                                        }
                                        catch (PermissionDeniedException e) {
                                            error( e.getLocalizedMessage() );
                                            return null;
                                        }
                                    }
                                } ) {
                                    @Override
                                    protected void populateItem(final ListItem<ProfileItem> profileItemListItem) {

                                        profileItemListItem.add(
                                                new Label(
                                                        "label", profileItemListItem.getModelObject().getType().getLocalizedInstance() ) );
                                        profileItemListItem.add(
                                                new TextField<String>(
                                                        "value", new IModel<String>() {
                                                    @Override
                                                    public String getObject() {

                                                        return profileItemListItem.getModelObject().getValue();
                                                    }

                                                    @Override
                                                    public void setObject(final String object) {

                                                        profileItemListItem.getModelObject().setValue( object );
                                                    }

                                                    @Override
                                                    public void detach() {

                                                        userService.save( profile );
                                                    }
                                                } ).add( new AjaxUpdatingBehaviour() ) );
                                    }
                                } );
                    }

                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( showProfile && GrantMyWishesSession.get().getUser() != null && !entry.getModelObject().isEmpty() );
                    }
                } );
        add(
                new WebMarkupContainer( "wishes" ) {
                    public ListView<Wish> entry;

                    @Override
                    protected void onInitialize() {

                        super.onInitialize();

                        add(
                                entry = new ListView<Wish>(
                                        "entry", new LoadableDetachableModel<List<? extends Wish>>() {
                                    @Override
                                    protected List<? extends Wish> load() {

                                        return ImmutableList.copyOf(
                                                wishService.getWishes( Predicates.<Wish>alwaysTrue() ) );
                                    }
                                } ) {
                                    @Override
                                    protected void populateItem(final ListItem<Wish> wishListItem) {

                                        wishListItem.add(
                                                new Label(
                                                        "label", wishListItem.getModelObject().getName() ) );
                                        wishListItem.add(
                                                new TextField<String>(
                                                        "value", new LoadableDetachableModel<String>() {
                                                    @Override
                                                    protected String load() {

                                                        return wishListItem.getModelObject().getDescription();
                                                    }
                                                } ) );
                                    }
                                } );
                    }

                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( showWishes && !entry.getModelObject().isEmpty() );
                    }
                } );
        add(
                wishLists = new WebMarkupContainer( "wishLists" ) {
                    public ListView<WishList> entry;
                    public boolean newWishListForm;

                    @Override
                    protected void onInitialize() {

                        super.onInitialize();

                        add(
                                entry = new ListView<WishList>(
                                        "entry", new LoadableDetachableModel<List<? extends WishList>>() {
                                    @Override
                                    protected List<? extends WishList> load() {

                                        try {
                                            return ImmutableList.copyOf(
                                                    userService.getWishLists( checkNotNull( GrantMyWishesSession.get().getUser() ) ) );
                                        }
                                        catch (PermissionDeniedException e) {
                                            error( e.getLocalizedMessage() );
                                            return null;
                                        }
                                    }
                                } ) {
                                    @Override
                                    protected void populateItem(final ListItem<WishList> wishListListItem) {

                                        wishListListItem.add( new AjaxLink<Void>( "link" ) {

                                            @Override
                                            protected void onInitialize() {

                                                super.onInitialize();

                                                add( new Label( "label", wishListListItem.getModel() ) );
                                            }

                                            @Override
                                            public void onClick(final AjaxRequestTarget target) {

                                                try {
                                                    SectionNavigationController.get()
                                                                               .activateTabWithState(
                                                                                       SectionInfo.WISHES,
                                                                                       new SectionContentWishes.SectionStateWishes(
                                                                                               wishListListItem.getModelObject() ) );
                                                }
                                                catch (IncompatibleStateException e) {
                                                    error( e );
                                                }
                                            }
                                        });
                                    }
                                } );
                        add(
                                new AjaxLink<Void>( "newWishList" ) {

                                    @Override
                                    public void onClick(final AjaxRequestTarget target) {

                                        newWishListForm = true;
                                        target.addComponent( wishLists );
                                    }

                                    @Override
                                    protected void onConfigure() {

                                        super.onConfigure();

                                        setVisible( !newWishListForm );
                                    }
                                } );
                        final Model<String> newWishListName = Model.of();
                        add(
                                new TextField<String>( "newWishList.name", newWishListName ) {

                                    @Override
                                    protected void onConfigure() {

                                        super.onConfigure();

                                        setVisible( newWishListForm );
                                    }
                                }.add(
                                        new AjaxSubmitBehavior() {
                                            @Override
                                            protected void onUpdate(final AjaxRequestTarget target) {

                                                super.onUpdate( target );

                                                try {
                                                    userService.newWishList(
                                                            newWishListName.getObject(),
                                                            userService.getProfile( GrantMyWishesSession.get().getUser() ) );

                                                    newWishListForm = false;
                                                    target.addComponent( wishLists );
                                                }
                                                catch (PermissionDeniedException e) {
                                                    error( e );
                                                }
                                            }
                                        } ) );
                    }

                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( showWishLists && GrantMyWishesSession.get().getUser() != null );
                    }
                }.setOutputMarkupPlaceholderTag( true ) );
    }

    public void select(final boolean profile, final boolean wishes, final boolean wishLists) {

        showProfile = profile;
        showWishes = wishes;
        showWishLists = wishLists;

        SectionNavigationController.get().activateTab( SectionInfo.USER, this );
    }

    public static class SectionStateUser extends SectionState<SectionContentUser> {

        public SectionStateUser(final String fragment) {

            super( fragment );
        }

        public SectionStateUser(final SectionContentUser panel) {

            super( panel );
        }

        @Override
        protected List<String> loadFragments(final SectionContentUser panel) {

            ImmutableList.Builder<String> builder = ImmutableList.builder();
            if (panel.showProfile)
                builder.add( "profile" );
            if (panel.showWishes)
                builder.add( "wishes" );
            if (panel.showWishLists)
                builder.add( "wishlists" );
            if (panel.query != null)
                builder.add( "q" ).add( panel.query );

            return builder.build();
        }

        @Override
        protected void applyFragments(final SectionContentUser panel, final Deque<String> fragments) {

            panel.select( false, false, false );

            FragmentSection section = FragmentSection.SHOW;
            while (!fragments.isEmpty()) {
                String fragment = fragments.pop();

                switch (section) {
                    case SHOW:
                        if (ObjectUtils.isEqual( "profile", fragment ))
                            panel.showProfile = true;
                        else if (ObjectUtils.isEqual( "wishes", fragment ))
                            panel.showWishes = true;
                        else if (ObjectUtils.isEqual( "wishlists", fragment ))
                            panel.showWishLists = true;
                        else if (ObjectUtils.isEqual( "q", fragment ))
                            section = FragmentSection.QUERY;
                        else
                            throw logger.bug( "Unsupported %s fragment: %s", section, fragment );

                        break;
                    case QUERY:
                        panel.query = fragment;
                        section = FragmentSection.END;
                        break;
                    case END:
                        throw logger.bug( "Not expecting any more fragments, but found: %s", fragment );
                }
            }
        }

        enum FragmentSection {
            SHOW,
            QUERY,
            END
        }
    }


    interface Messages {

    }
}
