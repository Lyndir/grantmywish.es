package com.lyndir.lhunath.grantmywishes.webapp.section;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.*;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.grantmywishes.model.service.WishService;
import com.lyndir.lhunath.grantmywishes.webapp.GrantMyWishesSession;
import com.lyndir.lhunath.opal.security.error.PermissionDeniedException;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import com.lyndir.lhunath.opal.system.logging.Logger;
import com.lyndir.lhunath.opal.wayward.behavior.*;
import com.lyndir.lhunath.opal.wayward.component.AjaxLabelLink;
import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import java.util.*;
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

    String  query;
    boolean showProfile;
    boolean showWishes;
    boolean showWishLists;

    Component wishLists;
    boolean   newWishListForm;

    public SectionContentUser(final String id) {

        super( id );

        showProfile = true;
        showWishLists = true;
    }

    @Override
    protected void onInitialize() {

        super.onInitialize();

        add( new WebMarkupContainer( "notLoggedIn" ) {
            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( GrantMyWishesSession.get().getUser() == null );
            }
        } );
        add( new Label( "query", new LoadableDetachableModel<String>() {
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
        add( new WebMarkupContainer( "noQuery" ) {
            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( query == null && GrantMyWishesSession.get().getUser() != null );
            }
        } );

        add( new WebMarkupContainer( "profile" ) {
            Profile profile;
            public ListView<ProfileItem> entry;

            @Override
            protected void onInitialize() {

                super.onInitialize();

                add( entry = new ListView<ProfileItem>( "entry", new LoadableDetachableModel<List<? extends ProfileItem>>() {
                    @Override
                    protected List<? extends ProfileItem> load() {

                        try {
                            Collection<ProfileItem> profileItemValues = (profile = userService.getProfile(
                                    checkNotNull( GrantMyWishesSession.get().getUser() ) )).getProfileItems().values();

                            return ImmutableList.copyOf( Collections2.filter( profileItemValues, new Predicate<ProfileItem>() {
                                @Override
                                public boolean apply(final ProfileItem input) {

                                    return query == null || input.getType().getLocalizedInstance().contains( query ) || input.getValue()
                                                                                                                             .contains(
                                                                                                                                     query );
                                }
                            } ) );
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
                                new Label( "label", profileItemListItem.getModelObject().getType().getLocalizedInstance() ) );
                        profileItemListItem.add( new TextField<String>( "value", new IModel<String>() {
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
        add( new WebMarkupContainer( "wishes" ) {
            public ListView<Wish> entry;

            @Override
            protected void onInitialize() {

                super.onInitialize();

                add( entry = new ListView<Wish>( "entry", new LoadableDetachableModel<List<? extends Wish>>() {
                    @Override
                    protected List<? extends Wish> load() {

                        return ImmutableList.copyOf( wishService.getWishes( new Predicate<Wish>() {
                            @Override
                            public boolean apply(final Wish input) {

                                return query == null || input.getName().contains( query );
                            }
                        } ) );
                    }
                } ) {
                    @Override
                    protected void populateItem(final ListItem<Wish> wishListItem) {

                        wishListItem.add( new Label( "label", wishListItem.getModelObject().getName() ) );
                        wishListItem.add( new TextField<String>( "value", new LoadableDetachableModel<String>() {
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
        add( wishLists = new WebMarkupContainer( "wishLists" ) {
            ListView<WishList> entry;

            @Override
            protected void onInitialize() {

                super.onInitialize();

                add( entry = new ListView<WishList>( "entry", new LoadableDetachableModel<List<? extends WishList>>() {
                    @Override
                    protected List<? extends WishList> load() {

                        SizedIterator<WishList> wishLists = userService.getWishLists(
                                checkNotNull( GrantMyWishesSession.get().getUser() ) );

                        return ImmutableList.copyOf( Iterators.filter( wishLists, new Predicate<WishList>() {
                            @Override
                            public boolean apply(final WishList input) {

                                return query == null || input.getName().contains( query );
                            }
                        } ) );
                    }
                } ) {
                    @Override
                    protected void populateItem(final ListItem<WishList> wishListListItem) {

                        wishListListItem.add( new AjaxLabelLink<WishList>( "link", wishListListItem.getModel() ) {

                            @Override
                            public void onClick(final AjaxRequestTarget target) {

                                try {
                                    SectionNavigationController.get()
                                                               .activateTabWithState( SectionInfo.WISHES,
                                                                                      SectionContentWishes.SectionStateWishes
                                                                                              .wishList(
                                                                                                      wishListListItem.getModelObject() ) );
                                }
                                catch (IncompatibleStateException e) {
                                    error( e );
                                }
                            }
                        } );
                    }
                } );
                add( new AjaxLink<Void>( "newWishList" ) {

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
                add( new TextField<String>( "newWishList.name" ) {

                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( newWishListForm );
                    }
                }.add( new AjaxSubmitBehavior() {
                    @Override
                    protected void onUpdate(final AjaxRequestTarget target) {

                        super.onUpdate( target );

                        try {
                            userService.newWishList( getComponent().getDefaultModelObjectAsString(),
                                                     userService.getProfile( checkNotNull( GrantMyWishesSession.get().getUser() ) ) );

                            newWishListForm = false;
                            target.addComponent( wishLists );
                        }
                        catch (PermissionDeniedException e) {
                            error( e );
                        }
                    }
                } ).add( new FocusOnReady() ) );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( showWishLists && GrantMyWishesSession.get().getUser() != null );
            }
        }.setOutputMarkupPlaceholderTag( true ) );
    }

    @Override
    protected void onConfigure() {

        super.onConfigure();

        newWishListForm = false;
    }

    public static class SectionStateUser extends SectionState<SectionContentUser> {

        protected SectionStateUser(final List<String> fragments) {

            super( fragments );
        }

        public SectionStateUser(final String fragment) {

            super( fragment );
        }

        public SectionStateUser(final SectionContentUser panel) {

            super( panel );
        }

        public static SectionStateUser query(final String query) {

            return new SectionStateUser( ImmutableList.<String>of( "profile", "wishes", "wishLists", "q", query ) );
        }

        public static SectionStateUser profile() {

            return new SectionStateUser( ImmutableList.<String>of( "profile" ) );
        }

        public static SectionStateUser wishes() {

            return new SectionStateUser( ImmutableList.<String>of( "wishes" ) );
        }

        public static SectionStateUser wishLists() {

            return new SectionStateUser( ImmutableList.<String>of( "wishLists" ) );
        }

        @Override
        protected List<String> loadFragments(final SectionContentUser panel) {

            ImmutableList.Builder<String> builder = ImmutableList.builder();
            if (panel.showProfile)
                builder.add( "profile" );
            if (panel.showWishes)
                builder.add( "wishes" );
            if (panel.showWishLists)
                builder.add( "wishLists" );
            if (panel.query != null)
                builder.add( "q" ).add( panel.query );

            return builder.build();
        }

        @Override
        protected void applyFragments(final SectionContentUser panel, final Deque<String> fragments)
                throws IncompatibleStateException {

            panel.showProfile = false;
            panel.showWishes = false;
            panel.showWishLists = false;
            panel.query = null;

            while (!fragments.isEmpty()) {
                String fragment = fragments.pop();

                if ("profile".equalsIgnoreCase( fragment ))
                    panel.showProfile = true;
                else if ("wishes".equalsIgnoreCase( fragment ))
                    panel.showWishes = true;
                else if ("wishLists".equalsIgnoreCase( fragment ))
                    panel.showWishLists = true;
                else if ("q".equalsIgnoreCase( fragment ))
                    panel.query = fragments.pop();
                else
                    throw new IncompatibleStateException( "Unsupported fragment: %s", fragment );
            }
        }
    }


    interface Messages {

    }
}
