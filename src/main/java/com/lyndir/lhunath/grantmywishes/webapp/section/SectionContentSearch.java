package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import org.jetbrains.annotations.NotNull;


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

        public SectionStateSearch(final SectionContentSearch content) {

        }

        public SectionStateSearch(final String fragment) {

        }

        @Override
        public String toFragment() {

            return null;
        }

        @Override
        public void apply(@NotNull final SectionContentSearch panel)
                throws IncompatibleStateException {

        }
    }
}
