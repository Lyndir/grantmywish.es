package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import org.jetbrains.annotations.NotNull;


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

        public SectionStateAbout(final SectionContentAbout content) {

        }

        public SectionStateAbout(final String fragment) {

        }

        @Override
        public String toFragment() {

            return null;
        }

        @Override
        public void apply(@NotNull final SectionContentAbout panel)
                throws IncompatibleStateException {

        }
    }
}
