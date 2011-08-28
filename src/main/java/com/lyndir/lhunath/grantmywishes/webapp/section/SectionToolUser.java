package com.lyndir.lhunath.grantmywishes.webapp.section;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionToolUser extends SectionTool<SectionContentUser> {

    public SectionToolUser(final String id) {

        super( id );
    }

    @Override
    protected void onInitialize() {

        super.onInitialize();

        add(
                new AjaxLink<Void>( "myProfile" ) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {

                        SectionNavigationController.get().activateNewTab( SectionInfo.USER );
                        getContent().select( true, false, false );
                    }
                } );
        add(
                new AjaxLink<Void>( "myWishes" ) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {

                        getContent().select( false, true, false );
                    }
                } );
        add(
                new AjaxLink<Void>( "myWishLists" ) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {

                        getContent().select( false, false, true );
                    }
                } );
    }
}
