package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * <i>06 20, 2011</i>
 *
 * @author lhunath
 */
public abstract class SectionContent extends Panel {

    SectionContent(final String id) {

        super( id );
    }

    public List<? extends SectionContentLink> getLinks() {

        return ImmutableList.of();
    }
}
