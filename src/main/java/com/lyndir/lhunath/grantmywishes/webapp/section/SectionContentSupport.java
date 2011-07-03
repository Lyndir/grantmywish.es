package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import org.jetbrains.annotations.NotNull;


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

        public SectionStateSupport(final SectionContentSupport content) {

        }

        public SectionStateSupport(final String fragment) {

        }

        @Override
        public String toFragment() {

            return null;
        }

        @Override
        public void apply(@NotNull final SectionContentSupport panel)
                throws IncompatibleStateException {

        }
    }
}
