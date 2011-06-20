package com.lyndir.lhunath.grantmywishes.webapp.section;

/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentProfile extends SectionContent {

    public SectionContentProfile(final String id) {

        super( id );
    }

    public class SectionStateProfile implements SectionState {

        @Override
        public String toFragment() {

            return null;
        }
    }
}
