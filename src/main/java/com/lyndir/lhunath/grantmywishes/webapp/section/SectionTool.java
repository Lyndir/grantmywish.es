package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.grantmywishes.webapp.page.LayoutPage;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * <i>06 20, 2011</i>
 *
 * @author lhunath
 */
public abstract class SectionTool<C extends SectionContent> extends Panel {

    SectionTool(final String id) {

        super( id );
    }

    public C getContent() {

        return ((LayoutPage) getPage()).getContent( this );
    }
}
