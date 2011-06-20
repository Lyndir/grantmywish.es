package com.lyndir.lhunath.grantmywishes.webapp.section;

/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentSearch extends SectionContent {

    public SectionContentSearch(final String id) {

        super( id );
    }

    public static class SectionStateSearch implements SectionState {

        @Override
        public String toFragment() {

            return null;
        }
    }
}
