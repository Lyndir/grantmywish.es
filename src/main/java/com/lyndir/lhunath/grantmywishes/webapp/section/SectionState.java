package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.opal.wayward.navigation.AbstractTabState;
import java.util.List;


/**
 * <i>06 20, 2011</i>
 *
 * @author lhunath
 */
public abstract class SectionState<P extends SectionContent> extends AbstractTabState<P> {

    protected SectionState(final List<String> fragments) {

        super( fragments );
    }

    protected SectionState(final String fragment) {

        super( fragment );
    }

    protected SectionState(final P panel) {

        super( panel );
    }
}
