package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.google.common.collect.ImmutableList;
import java.util.Deque;
import java.util.List;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentAbout extends SectionContent {

    public SectionContentAbout(final String id) {

        super( id );
    }

    public static class SectionStateAbout extends SectionState<SectionContentAbout> {

        public SectionStateAbout(final String fragment) {

            super( fragment );
        }

        public SectionStateAbout(final SectionContentAbout panel) {

            super( panel );
        }

        @Override
        protected List<String> loadFragments(final SectionContentAbout panel) {

            return ImmutableList.of();
        }

        @Override
        protected void applyFragments(final SectionContentAbout panel, final Deque<String> fragments) {

        }
    }
}
