package com.lyndir.lhunath.grantmywishes.webapp.section;

import static com.google.common.base.Preconditions.*;
import static com.lyndir.lhunath.opal.system.util.ObjectUtils.*;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.*;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.grantmywishes.model.service.WishService;
import com.lyndir.lhunath.grantmywishes.webapp.GrantMyWishesSession;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import com.lyndir.lhunath.opal.system.logging.Logger;
import com.lyndir.lhunath.opal.system.util.ObjectUtils;
import com.lyndir.lhunath.opal.wayward.behavior.*;
import com.lyndir.lhunath.opal.wayward.component.*;
import com.lyndir.lhunath.opal.wayward.model.AbstractModel;
import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import com.lyndir.lhunath.opal.wayward.provider.AbstractSizedIteratorProvider;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentWishes extends SectionContent {

    static final Pattern PAST_FIRST_PARAGRAPH = Pattern.compile( "\n\n+.*", Pattern.DOTALL );

    static final Logger   logger = Logger.get( SectionContentWishes.class );
    static final Messages msgs   = MessagesFactory.create( Messages.class );

    @Inject
    UserService userService;

    @Inject
    WishService wishService;

    String query;

    User     user;
    WishList wishList;
    Wish     wish;
    final List<WishGroup> groups = Lists.newLinkedList();

    Component   wishes;
    ModalWindow addToWishListWindow;

    public SectionContentWishes(final String id) {

        super( id );
    }

    @Override
    public List<? extends SectionContentLink> getLinks() {

        ImmutableList.Builder<SectionContentLink> builder = ImmutableList.builder();
        if (wish != null && GrantMyWishesSession.get().getUser() != null)
            builder.add( new SectionContentLink() {
                @Override
                public String getTitle() {

                    return msgs.addWishToList();
                }

                @Override
                public void onClick(final AjaxRequestTarget target) {

                    addToWishListWindow.show( target );
                }
            } );

        return builder.build();
    }

    @Override
    protected void onInitialize() {

        super.onInitialize();

        add( new WebMarkupContainer( "notLoggedIn" ) {
            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( wishList == null && GrantMyWishesSession.get().getUser() == null );
            }
        } );
        add( new Label( "query", new LoadableDetachableModel<Object>() {
            @Override
            protected Object load() {

                return query;
            }
        } ) {
            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( query != null );
            }
        } );

        add( wishes = new WebMarkupContainer( "wishes" ) {
            public boolean newWish;

            @Override
            protected void onInitialize() {

                super.onInitialize();

                add( new DataView<Wish>( "items", new AbstractSizedIteratorProvider<Wish>() {
                    @Override
                    protected SizedIterator<Wish> load() {

                        return wishService.getWishes( new Predicate<Wish>() {
                            @Override
                            public boolean apply(final Wish input) {

                                boolean containedInGroups = groups.isEmpty();
                                for (final WishGroup group : groups)
                                    if (group.apply( wish, GrantMyWishesSession.get().getUser() )) {
                                        containedInGroups = true;
                                        break;
                                    }
                                if (!containedInGroups)
                                    return false;

                                return query == null || input.getName().contains( query );
                            }
                        } );
                    }
                } ) {
                    @Override
                    protected void populateItem(final Item<Wish> wishItem) {

                        wishItem.add( new AjaxLink<Void>( "link" ) {
                            @Override
                            protected void onInitialize() {

                                super.onInitialize();

                                final Wish wish = wishItem.getModelObject();
                                add( new Image( "image", new DynamicImageResource( "image" ) {
                                    @Override
                                    protected byte[] getImageData() {

                                        return wishItem.getModelObject().getImage();
                                    }
                                } ) );
                                add( new Label( "name", new LoadableDetachableModel<String>() {
                                    @Override
                                    protected String load() {

                                        return wish.getName();
                                    }
                                } ) );
                                add( new MultiLineLabel( "description", new LoadableDetachableModel<Object>() {

                                    @Override
                                    protected Object load() {

                                        String description = wish.getDescription();

                                        // First paragraph.
                                        description = PAST_FIRST_PARAGRAPH.matcher( description ).replaceFirst( "" );

                                        // Character limit.
                                        description = description.substring( 0, Math.min( description.length(), 200 ) );

                                        return description;
                                    }
                                } ) );
                            }

                            @Override
                            public void onClick(final AjaxRequestTarget target) {

                                try {
                                    SectionNavigationController.get()
                                                               .activateTabWithState( SectionInfo.WISHES, SectionStateWishes.wish(
                                                                       wishItem.getModelObject() ) );
                                }
                                catch (IncompatibleStateException e) {
                                    throw logger.bug( e );
                                }
                            }
                        } );
                    }
                } );

                add( new AjaxLink<Void>( "newWish" ) {

                    @Override
                    public void onClick(final AjaxRequestTarget target) {

                        newWish = true;
                        target.addComponent( wishes );
                    }

                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( !newWish && GrantMyWishesSession.get().getUser() != null );
                    }
                } );
                add( new TextField<String>( "newWish.name", Model.<String>of() ) {

                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( newWish && GrantMyWishesSession.get().getUser() != null );
                    }
                }.add( new AjaxSubmitBehavior() {
                    @Override
                    protected void onUpdate(final AjaxRequestTarget target) {

                        super.onUpdate( target );

                        Wish wish = wishService.update( new Wish( getComponent().getDefaultModelObjectAsString() ) );
                        newWish = false;

                        try {
                            SectionNavigationController.get().activateTabWithState( SectionInfo.WISHES, SectionStateWishes.wish( wish ) );
                        }
                        catch (IncompatibleStateException e) {
                            throw logger.bug( e );
                        }
                    }
                } ).add( new FocusOnReady() ) );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( query != null || !groups.isEmpty() );
            }
        }.setOutputMarkupId( true ) );
        add( new WebMarkupContainer( "wishList" ) {
            @Override
            protected void onInitialize() {

                super.onInitialize();

                add( new Label( "name", new LoadableDetachableModel<String>() {
                    @Override
                    protected String load() {

                        return wishList.getName();
                    }
                } ) );
                add( new ListView<WishListItem>( "items", new LoadableDetachableModel<List<? extends WishListItem>>() {
                    @Override
                    protected List<? extends WishListItem> load() {

                        return wishList.getItems();
                    }
                } ) {
                    @Override
                    protected void populateItem(final ListItem<WishListItem> wishListItemListItem) {

                        Wish wish = wishListItemListItem.getModelObject().getWish();
                        wishListItemListItem.add( new Image( "image", new DynamicImageResource( "image" ) {
                            @Override
                            protected byte[] getImageData() {

                                return wishListItemListItem.getModelObject().getWish().getImage();
                            }
                        } ) );
                        wishListItemListItem.add( new Label( "name", wish.getName() ) );
                        wishListItemListItem.add( new MultiLineLabel( "description", wish.getDescription() ) );
                    }
                } );
                add( new WebMarkupContainer( "newWish" ) {
                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setVisible( ObjectUtils.isEqual( GrantMyWishesSession.get().getUser(), wishList.getOwner() ) );
                    }
                } );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( wishList != null );
            }
        } );
        add( new WebMarkupContainer( "user" ) {
            @Override
            protected void onInitialize() {

                super.onInitialize();

                add( new Label( "name", new LoadableDetachableModel<String>() {
                    @Override
                    protected String load() {

                        return user.getName();
                    }
                } ) );
                add( new DataView<WishList>( "items", new AbstractSizedIteratorProvider<WishList>() {
                    @Override
                    protected SizedIterator<WishList> load() {

                        return userService.getWishLists( user );
                    }
                } ) {
                    @Override
                    protected void populateItem(final Item<WishList> wishListItem) {

                        wishListItem.add( new AjaxLabelLink<WishList>( "name", wishListItem.getModel() ) {
                            @Override
                            public void onClick(final AjaxRequestTarget target) {

                                try {
                                    SectionNavigationController.get()
                                                               .activateTabWithState( SectionInfo.WISHES,
                                                                                      SectionContentWishes.SectionStateWishes
                                                                                              .wishList( wishListItem.getModelObject() ) );
                                }
                                catch (IncompatibleStateException e) {
                                    error( e );
                                }
                            }
                        } );
                    }
                } );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( user != null && wishList == null );
            }
        } );
        add( new WebMarkupContainer( "wish" ) {
            @Override
            protected void onInitialize() {

                super.onInitialize();

                add( new AjaxEditableLabel<String>( "name", new AbstractModel<String>() {
                    @Override
                    public String getObject() {

                        return wish.getName();
                    }

                    @Override
                    public void setObject(final String object) {

                        wish.setName( object );
                        wishService.update( wish );
                    }
                } ) {
                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setEditable( GrantMyWishesSession.get().getUser() != null );
                    }
                } );
                add( new AjaxEditableImage( "image" ) {
                    @Override
                    protected byte[] getImageData() {

                        return wish.getImage();
                    }

                    @Override
                    protected void setImageData(final byte[] imageData) {

                        wish.setImage( imageData );
                        wishService.update( wish );
                    }

                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setEditable( GrantMyWishesSession.get().getUser() != null );
                    }
                } );
                add( new AjaxEditableLabel<String>( "description", new AbstractModel<String>() {
                    @Override
                    public String getObject() {

                        return ifNotNullElse( wish.getDescription(), msgs.emptyEditableWishDescription() );
                    }

                    @Override
                    public void setObject(final String object) {

                        wish.setDescription( object );
                        wishService.update( wish );
                    }
                } ) {
                    @Override
                    protected void onConfigure() {

                        super.onConfigure();

                        setEditable( GrantMyWishesSession.get().getUser() != null );
                    }
                }.setMultiline( true ) );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( wish != null );
            }
        } );
        addToWishListWindow = new ModalWindow( "addToWishListWindow" ) {

            @Override
            protected void onInitialize() {

                super.onInitialize();

                add( new DropDownChoice<WishList>( "addToWishListWindow.list", new AbstractModel<WishList>() {
                    @Override
                    public WishList getObject() {

                        return null;
                    }

                    @Override
                    public void setObject(final WishList object) {

                        object.getItems().add( new WishListItem( wish ) );
                        userService.save( object );

                        addToWishListWindow.close( AjaxRequestTarget.get() );
                    }
                }, new LoadableDetachableModel<List<? extends WishList>>() {
                    @Override
                    protected List<? extends WishList> load() {

                        return ImmutableList.copyOf( userService.getWishLists( GrantMyWishesSession.get().getUser() ) );
                    }
                }
                ).add( new AjaxUpdatingBehaviour() ) );
            }
        };
        add( addToWishListWindow );
    }

    void setUser(final String ownerName) {

        user = userService.getUser( ownerName );
    }

    void setWishList(final String wishListName) {

        wishList = userService.getWishList( user, wishListName );
    }

    void setWish(final String wishName) {

        wish = wishService.getWish( wishName );
    }

    public static class SectionStateWishes extends SectionState<SectionContentWishes> {

        protected SectionStateWishes(final List<String> fragments) {

            super( fragments );
        }

        public SectionStateWishes(final String fragment) {

            super( fragment );
        }

        public SectionStateWishes(final SectionContentWishes panel) {

            super( panel );
        }

        public static SectionStateWishes query(final String query) {

            return new SectionStateWishes( ImmutableList.<String>of( "q", query ) );
        }

        public static SectionStateWishes user(final User user) {

            return new SectionStateWishes( ImmutableList.of( "user", user.getName() ) );
        }

        public static SectionStateWishes wishList(final WishList wishList) {

            return new SectionStateWishes( ImmutableList.of( "user", wishList.getOwner().getName(), "wishList", wishList.getName() ) );
        }

        public static SectionStateWishes wish(final Wish wish) {

            return new SectionStateWishes( ImmutableList.of( "wish", wish.getName() ) );
        }

        public static SectionStateWishes in(final WishGroup... groups) {

            return new SectionStateWishes( Lists.transform( ImmutableList.copyOf( groups ), new Function<WishGroup, String>() {
                @Override
                public String apply(final WishGroup input) {

                    return input.name().toLowerCase();
                }
            } ) );
        }

        @Override
        protected List<String> loadFragments(final SectionContentWishes panel) {

            ImmutableList.Builder<String> builder = ImmutableList.builder();
            if (panel.query != null)
                builder.add( "q" ).add( panel.query );
            if (panel.user != null)
                builder.add( "user" ).add( panel.user.getName() );
            if (panel.wishList != null) {
                checkState( ObjectUtils.isEqual( panel.user, panel.wishList.getOwner() ),
                            "Panel user doesn't equal panel wish list's owner." );
                builder.add( "wishList" ).add( panel.wishList.getName() );
            }
            if (panel.wish != null)
                builder.add( "wish" ).add( panel.wish.getName() );
            for (final WishGroup group : panel.groups)
                builder.add( group.name().toLowerCase() );

            return builder.build();
        }

        @Override
        protected void applyFragments(final SectionContentWishes panel, final Deque<String> fragments)
                throws IncompatibleStateException {

            if (fragments.isEmpty())
                return;

            panel.query = null;
            panel.user = null;
            panel.wishList = null;
            panel.wish = null;
            panel.groups.clear();

            fragments:
            while (!fragments.isEmpty()) {
                String fragment = fragments.pop();

                if ("q".equals( fragment ))
                    panel.query = fragments.pop();
                else if ("user".equals( fragment ))
                    panel.setUser( fragments.pop() );
                else if ("wishList".equals( fragment ))
                    panel.setWishList( fragments.pop() );
                else if ("wish".equals( fragment ))
                    panel.setWish( fragments.pop() );
                else {
                    for (final WishGroup group : WishGroup.values())
                        if (group.name().toLowerCase().equals( fragment )) {
                            panel.groups.add( group );
                            continue fragments;
                        }

                    throw new IncompatibleStateException( "Don't know how to handle fragment: %s, remaining fragments: %s", fragment,
                                                          fragments );
                }
            }
        }
    }


    interface Messages {

        String emptyEditableWishDescription();

        String addWishToList();
    }
}
