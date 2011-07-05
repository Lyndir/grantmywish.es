package com.lyndir.lhunath.grantmywishes.webapp.section;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionToolProfile extends SectionTool<SectionContentUser> {

    public SectionToolProfile(final String id, final SectionContentUser content) {

        super( id, content );
    }

    @Override
    protected void onInitialize() {

        super.onInitialize();

        add(
                new AjaxLink<Void>( "myProfile" ) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {

                        getContent().selectProfile();
                    }
                } );
    }
}
