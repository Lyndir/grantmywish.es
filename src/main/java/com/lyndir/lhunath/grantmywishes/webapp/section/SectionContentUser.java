package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.lyndir.lhunath.grantmywishes.data.ProfileItem;
import com.lyndir.lhunath.grantmywishes.model.service.UserService;
import com.lyndir.lhunath.grantmywishes.webapp.GrantMyWishesSession;
import com.lyndir.lhunath.opal.wayward.i18n.MessagesFactory;
import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.jetbrains.annotations.NotNull;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentUser extends SectionContent {

    static final Messages msgs = MessagesFactory.create( Messages.class );

    @Inject
    UserService userService;

    private String query;

    public SectionContentUser(final String id) {

        super( id );
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

                        setVisible( query != null );
                    }
                } );

        add(
                new WebMarkupContainer( "profile" ) {
                    @Override
                    protected void onInitialize() {

                        super.onInitialize();

                        add(
                                new ListView<ProfileItem>(
                                        "entry", new LoadableDetachableModel<List<? extends ProfileItem>>() {
                                    @Override
                                    protected List<? extends ProfileItem> load() {

                                        return ImmutableList.copyOf(
                                                userService.getProfile( GrantMyWishesSession.get().getUser() ).getProfileItems() );
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

                                                    }
                                                } ) );
                                    }
                                } );
                    }
                } );
    }

    public void selectProfile() {

        throw new UnsupportedOperationException( "TODO: Implement selectProfile in SectionContentProfile" );
    }

    public static class SectionStateProfile extends SectionState<SectionContentUser> {

        public SectionStateProfile(final SectionContentUser content) {

        }

        public SectionStateProfile(final String fragment) {

        }

        @Override
        public String toFragment() {

            return null;
        }

        @Override
        public void apply(@NotNull final SectionContentUser panel)
                throws IncompatibleStateException {

        }
    }


    interface Messages {

    }
}
