package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.google.common.collect.ImmutableList;
import java.util.Deque;
import java.util.List;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentSupport extends SectionContent {

    public SectionContentSupport(final String id) {

        super( id );
    }

    public static class SectionStateSupport extends SectionState<SectionContentSupport> {

        public SectionStateSupport(final String fragment) {

            super( fragment );
        }

        public SectionStateSupport(final SectionContentSupport panel) {

            super( panel );
        }

        @Override
        protected List<String> loadFragments(final SectionContentSupport panel) {

            return ImmutableList.of();
        }

        @Override
        protected void applyFragments(final SectionContentSupport panel, final Deque<String> fragments) {

        }
    }
}
