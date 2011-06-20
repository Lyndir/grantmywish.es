package com.lyndir.lhunath.grantmywishes.webapp.section;

/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentSupport extends SectionContent {

    public SectionContentSupport(final String id) {

        super( id );
    }

    public class SectionStateSupport implements SectionState {

        @Override
        public String toFragment() {

            return null;
        }
    }
}
