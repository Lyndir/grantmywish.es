package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.opal.system.logging.Logger;
import com.lyndir.lhunath.opal.wayward.behavior.AjaxSubmitBehavior;
import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionToolUser extends SectionTool<SectionContentUser> {

    static final Logger logger = Logger.get( SectionToolUser.class );

    public SectionToolUser(final String id) {

        super( id );
    }

    @Override
    protected void onInitialize() {

        super.onInitialize();

        add( new TextField<String>( "query", Model.<String>of() ).add( new AjaxSubmitBehavior() {
            @Override
            protected void onUpdate(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.USER, SectionContentUser.SectionStateUser
                                                       .query( getComponent().getDefaultModelObjectAsString() ) );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } ) );

        add( new AjaxLink<Void>( "myProfile" ) {
            @Override
            public void onClick(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.USER, SectionContentUser.SectionStateUser.profile() );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } );
        add( new AjaxLink<Void>( "myWishes" ) {
            @Override
            public void onClick(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.USER, SectionContentUser.SectionStateUser.wishes() );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } );
        add( new AjaxLink<Void>( "myWishLists" ) {
            @Override
            public void onClick(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.USER, SectionContentUser.SectionStateUser.wishLists() );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } );
    }
}
