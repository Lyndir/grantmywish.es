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
public class SectionToolSearch extends SectionTool<SectionContentSearch> {

    static final Logger logger = Logger.get( SectionToolSearch.class );

    public SectionToolSearch(final String id) {

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
                                               .activateTabWithState( SectionInfo.SEARCH, SectionContentSearch.SectionStateSearch
                                                       .query( getComponent().getDefaultModelObjectAsString() ) );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } ) );

        add( new AjaxLink<Void>( "myFriends" ) {
            @Override
            public void onClick(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.SEARCH,
                                                                      SectionContentSearch.SectionStateSearch.friends() );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } );
        add( new AjaxLink<Void>( "recentPeople" ) {
            @Override
            public void onClick(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.SEARCH,
                                                                      SectionContentSearch.SectionStateSearch.recent() );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } );
        add( new AjaxLink<Void>( "randomPeople" ) {
            @Override
            public void onClick(final AjaxRequestTarget target) {

                try {
                    SectionNavigationController.get()
                                               .activateTabWithState( SectionInfo.SEARCH,
                                                                      SectionContentSearch.SectionStateSearch.random() );
                }
                catch (IncompatibleStateException e) {
                    throw logger.bug( e );
                }
            }
        } );
    }
}
