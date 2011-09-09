package com.lyndir.lhunath.grantmywishes.webapp.section;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.*;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.grantmywishes.model.service.WishService;
import com.lyndir.lhunath.grantmywishes.webapp.GrantMyWishesSession;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import com.lyndir.lhunath.opal.system.util.ObjectUtils;
import com.lyndir.lhunath.opal.wayward.component.AjaxLabelLink;
import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import com.lyndir.lhunath.opal.wayward.provider.AbstractSizedIteratorProvider;
import java.util.Deque;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.LoadableDetachableModel;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentWishes extends SectionContent {

    @Inject
    UserService userService;

    @Inject
    WishService wishService;

    String query;

    WishList wishList;
    User     user;

    public SectionContentWishes(final String id) {

        super( id );
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

        add( new WebMarkupContainer( "wishes" ) {
            @Override
            protected void onInitialize() {

                super.onInitialize();

                add( new DataView<Wish>( "items", new AbstractSizedIteratorProvider<Wish>() {
                    @Override
                    protected SizedIterator<Wish> load() {

                        return wishService.getWishes( Predicates.<Wish>alwaysTrue() );
                    }
                } ) {
                    @Override
                    protected void populateItem(final Item<Wish> wishItem) {

                        Wish wish = wishItem.getModelObject();
                        wishItem.add( new Image( "image", new DynamicImageResource( "image" ) {
                            @Override
                            protected byte[] getImageData() {

                                return wishItem.getModelObject().getImage();
                            }
                        } ) );
                        wishItem.add( new Label( "name", wish.getName() ) );
                        wishItem.add( new MultiLineLabel( "description", wish.getDescription() ) );
                    }
                } );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( query != null );
            }
        } );
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
    }

    void setUser(final String ownerName) {

        user = userService.getUser( ownerName );
    }

    void setWishList(final String wishListName) {

        wishList = userService.getWishList( user, wishListName );
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

        public static SectionStateWishes user(final User user) {

            return new SectionStateWishes( ImmutableList.of( "user", user.getName() ) );
        }

        public static SectionStateWishes wishList(final WishList wishList) {

            return new SectionStateWishes( ImmutableList.of( "user", wishList.getOwner().getName(), "wishList", wishList.getName() ) );
        }

        @Override
        protected List<String> loadFragments(final SectionContentWishes panel) {

            ImmutableList.Builder<String> builder = ImmutableList.builder();
            if (panel.user != null)
                builder.add( "user" ).add( panel.user.getName() );
            if (panel.wishList != null) {
                checkState( ObjectUtils.isEqual( panel.user, panel.wishList.getOwner() ),
                            "Panel user doesn't equal panel wish list's owner." );
                builder.add( "wishList" ).add( panel.wishList.getName() );
            }

            return builder.build();
        }

        @Override
        protected void applyFragments(final SectionContentWishes panel, final Deque<String> fragments)
                throws IncompatibleStateException {

            if (fragments.isEmpty())
                return;

            while (!fragments.isEmpty()) {
                String fragment = fragments.pop();

                if ("user".equals( fragment ))
                    panel.setUser( fragments.pop() );
                else if ("wishList".equals( fragment ))
                    panel.setWishList( fragments.pop() );
                else
                    throw new IncompatibleStateException();
            }
        }
    }
}
