package com.lyndir.lhunath.grantmywishes.webapp.section;

import static com.lyndir.lhunath.opal.system.CodeUtils.*;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.Profile;
import com.lyndir.lhunath.grantmywishes.data.ProfileItemType;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.opal.system.MessageDigests;
import com.lyndir.lhunath.opal.system.collection.SizedIterator;
import com.lyndir.lhunath.opal.system.i18n.MessagesFactory;
import com.lyndir.lhunath.opal.system.logging.Logger;
import com.lyndir.lhunath.opal.wayward.component.AjaxLabelLink;
import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import com.lyndir.lhunath.opal.wayward.provider.AbstractSizedIteratorProvider;
import java.util.Deque;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentSearch extends SectionContent {

    static final Logger   logger = Logger.get( SectionContentSearch.class );
    static final Messages msgs   = MessagesFactory.create( Messages.class );

    @Inject
    UserService userService;

    String query;
    private boolean showFriends;
    private boolean showRecent;
    private boolean showRandom;

    public SectionContentSearch(final String id) {

        super( id );
    }

    @Override
    protected void onInitialize() {

        super.onInitialize();

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

        add( new DataView<Profile>( "people", new AbstractSizedIteratorProvider<Profile>() {
            @Override
            protected SizedIterator<Profile> load() {

                return userService.getProfiles( new Predicate<Profile>() {
                    @Override
                    public boolean apply(final Profile input) {

                        return input.getOwner().getName().contains( query );
                    }
                } );
            }
        } ) {
            @Override
            protected void populateItem(final Item<Profile> profileItem) {

                Profile profile = profileItem.getModelObject();
                profileItem.add( new ContextImage( "image", String.format( "http://www.gravatar.com/avatar/%s",
                                                                           hex( digest( MessageDigests.MD5.get(),
                                                                                        profile.getProfileValue( ProfileItemType.EMAIL )
                                                                                               .toLowerCase(), Charsets.UTF_8 ) ) ) ) );
                profileItem.add( new Label( "name", profile.getOwner().getName() ) );
                profileItem.add( new Label( "statusName", profile.getOwner().getName() ) );
                profileItem.add( new AjaxLabelLink<String>( "wishLists", msgs.statusWishLists(
                        userService.getWishLists( profile.getOwner() ).size() ) ) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {

                        try {
                            SectionNavigationController.get()
                                                       .activateTabWithState( SectionInfo.WISHES, SectionContentWishes.SectionStateWishes
                                                               .user( profileItem.getModelObject().getOwner() ) );
                        }
                        catch (IncompatibleStateException e) {
                            error( e );
                        }
                    }
                } );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( !showFriends && !showRecent && !showRandom && query != null );
            }
        } );

        add( new DataView<Profile>( "friends", new AbstractSizedIteratorProvider<Profile>() {
            @Override
            protected SizedIterator<Profile> load() {

                return userService.getProfiles( new Predicate<Profile>() {
                    @Override
                    public boolean apply(final Profile input) {

                        return query == null || input.getOwner().getName().contains( query );
                    }
                } );
            }
        } ) {
            @Override
            protected void populateItem(final Item<Profile> profileItem) {

                Profile profile = profileItem.getModelObject();
                profileItem.add( new ContextImage( "image", String.format( "http://www.gravatar.com/avatar/%s",
                                                                           hex( digest( MessageDigests.MD5.get(),
                                                                                        profile.getProfileValue( ProfileItemType.EMAIL )
                                                                                               .toLowerCase(), Charsets.UTF_8 ) ) ) ) );
                profileItem.add( new Label( "name", profile.getOwner().getName() ) );
                profileItem.add( new Label( "statusName", profile.getOwner().getName() ) );
                profileItem.add( new AjaxLabelLink<String>( "wishLists", msgs.statusWishLists(
                        userService.getWishLists( profile.getOwner() ).size() ) ) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {

                        try {
                            SectionNavigationController.get()
                                                       .activateTabWithState( SectionInfo.WISHES, SectionContentWishes.SectionStateWishes
                                                               .user( profileItem.getModelObject().getOwner() ) );
                        }
                        catch (IncompatibleStateException e) {
                            error( e );
                        }
                    }
                } );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( showFriends );
            }
        } );

        add( new DataView<Profile>( "recent", new AbstractSizedIteratorProvider<Profile>() {
            @Override
            protected SizedIterator<Profile> load() {

                return userService.getProfiles( new Predicate<Profile>() {
                    @Override
                    public boolean apply(final Profile input) {

                        return query == null || input.getOwner().getName().contains( query );
                    }
                } );
            }
        } ) {
            @Override
            protected void populateItem(final Item<Profile> profileItem) {

                Profile profile = profileItem.getModelObject();
                profileItem.add( new ContextImage( "image", String.format( "http://www.gravatar.com/avatar/%s",
                                                                           hex( digest( MessageDigests.MD5.get(),
                                                                                        profile.getProfileValue( ProfileItemType.EMAIL )
                                                                                               .toLowerCase(), Charsets.UTF_8 ) ) ) ) );
                profileItem.add( new Label( "name", profile.getOwner().getName() ) );
                profileItem.add( new Label( "statusName", profile.getOwner().getName() ) );
                profileItem.add( new AjaxLabelLink<String>( "wishLists", msgs.statusWishLists(
                        userService.getWishLists( profile.getOwner() ).size() ) ) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {

                        try {
                            SectionNavigationController.get()
                                                       .activateTabWithState( SectionInfo.WISHES, SectionContentWishes.SectionStateWishes
                                                               .user( profileItem.getModelObject().getOwner() ) );
                        }
                        catch (IncompatibleStateException e) {
                            error( e );
                        }
                    }
                } );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( showRecent );
            }
        } );

        add( new DataView<Profile>( "random", new AbstractSizedIteratorProvider<Profile>() {
            @Override
            protected SizedIterator<Profile> load() {

                return userService.getProfiles( new Predicate<Profile>() {
                    @Override
                    public boolean apply(final Profile input) {

                        return query == null || input.getOwner().getName().contains( query );
                    }
                } );
            }
        } ) {
            @Override
            protected void populateItem(final Item<Profile> profileItem) {

                Profile profile = profileItem.getModelObject();
                profileItem.add( new ContextImage( "image", String.format( "http://www.gravatar.com/avatar/%s",
                                                                           hex( digest( MessageDigests.MD5.get(),
                                                                                        profile.getProfileValue( ProfileItemType.EMAIL )
                                                                                               .toLowerCase(), Charsets.UTF_8 ) ) ) ) );
                profileItem.add( new Label( "name", profile.getOwner().getName() ) );
                profileItem.add( new Label( "statusName", profile.getOwner().getName() ) );
                profileItem.add( new AjaxLabelLink<String>( "wishLists", msgs.statusWishLists(
                        userService.getWishLists( profile.getOwner() ).size() ) ) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {

                        try {
                            SectionNavigationController.get()
                                                       .activateTabWithState( SectionInfo.WISHES, SectionContentWishes.SectionStateWishes
                                                               .user( profileItem.getModelObject().getOwner() ) );
                        }
                        catch (IncompatibleStateException e) {
                            error( e );
                        }
                    }
                } );
            }

            @Override
            protected void onConfigure() {

                super.onConfigure();

                setVisible( showRandom && query == null );
            }
        } );
    }

    public static class SectionStateSearch extends SectionState<SectionContentSearch> {

        protected SectionStateSearch(final List<String> fragments) {

            super( fragments );
        }

        public SectionStateSearch(final String fragment) {

            super( fragment );
        }

        public SectionStateSearch(final SectionContentSearch panel) {

            super( panel );
        }

        public static SectionStateSearch query(final String query) {

            return new SectionStateSearch( ImmutableList.<String>of( "q", query ) );
        }

        public static SectionStateSearch friends() {

            return new SectionStateSearch( ImmutableList.<String>of( "friends" ) );
        }

        public static SectionStateSearch recent() {

            return new SectionStateSearch( ImmutableList.<String>of( "recent" ) );
        }

        public static SectionStateSearch random() {

            return new SectionStateSearch( ImmutableList.<String>of( "random" ) );
        }

        @Override
        protected List<String> loadFragments(final SectionContentSearch panel) {

            ImmutableList.Builder<String> builder = ImmutableList.builder();
            if (panel.showFriends)
                builder.add( "friends" );
            if (panel.showRecent)
                builder.add( "recent" );
            if (panel.showRandom)
                builder.add( "random" );
            if (panel.query != null)
                builder.add( "q" ).add( panel.query );

            return builder.build();
        }

        @Override
        protected void applyFragments(final SectionContentSearch panel, final Deque<String> fragments) {

            while (!fragments.isEmpty()) {
                String fragment = fragments.pop();

                if ("friends".equalsIgnoreCase( fragment ))
                    panel.showFriends = true;
                else if ("recent".equalsIgnoreCase( fragment ))
                    panel.showRecent = true;
                else if ("random".equalsIgnoreCase( fragment ))
                    panel.showRandom = true;
                else if ("q".equalsIgnoreCase( fragment ))
                    panel.query = fragments.pop();
                else
                    throw logger.bug( "Unsupported fragment: %s", fragment );
            }
        }
    }


    interface Messages {

        IModel<String> statusWishLists(int wishListsCount);
    }
}
