package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.google.common.collect.ImmutableList;
import java.util.Deque;
import java.util.List;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentSearch extends SectionContent {

    public SectionContentSearch(final String id) {

        super( id );
    }

    public static class SectionStateSearch extends SectionState<SectionContentSearch> {

        public SectionStateSearch(final String fragment) {

            super( fragment );
        }

        public SectionStateSearch(final SectionContentSearch panel) {

            super( panel );
        }

        @Override
        protected List<String> loadFragments(final SectionContentSearch panel) {

            return ImmutableList.of();
        }

        @Override
        protected void applyFragments(final SectionContentSearch panel, final Deque<String> fragments) {

        }
    }
}
