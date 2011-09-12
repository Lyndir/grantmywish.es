package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.grantmywishes.data.WishGroup;
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
public class SectionToolWishes extends SectionTool<SectionContentWishes> {

    static final Logger logger = Logger.get( SectionToolWishes.class );

    public SectionToolWishes(final String id) {

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
                                               .activateTabWithState( SectionInfo.WISHES, SectionContentWishes.SectionStateWishes
                                                       .query( getComponent().getDefaultModelObjectAsString() ) );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } ) );

        add( new AjaxLink<Void>( "friends" ) {
            @Override
            public void onClick(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.WISHES, SectionContentWishes.SectionStateWishes.in(
                                                       WishGroup.FRIENDS ) );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } );
        add( new AjaxLink<Void>( "hot" ) {
            @Override
            public void onClick(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.WISHES, SectionContentWishes.SectionStateWishes.in(
                                                       WishGroup.HOT ) );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } );
        add( new AjaxLink<Void>( "recommended" ) {
            @Override
            public void onClick(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.WISHES, SectionContentWishes.SectionStateWishes.in(
                                                       WishGroup.RECOMMENDATIONS ) );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } );
    }
}
