package com.lyndir.lhunath.grantmywishes.webapp.section;

import org.apache.wicket.markup.html.panel.Panel;


/**
 * <i>06 20, 2011</i>
 *
 * @author lhunath
 */
public abstract class SectionTool<C extends SectionContent> extends Panel {

    private final C content;

    SectionTool(final String id, final C content) {

        super( id );

        this.content = content;
    }

    public C getContent() {

        return content;
    }
}
