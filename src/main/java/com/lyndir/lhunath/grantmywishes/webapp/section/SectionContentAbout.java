package com.lyndir.lhunath.grantmywishes.webapp.section;

/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentAbout extends SectionContent {

    public SectionContentAbout(final String id) {

        super( id );
    }

    public static class SectionStateAbout implements SectionState {

        @Override
        public String toFragment() {

            return null;
        }
    }
}
