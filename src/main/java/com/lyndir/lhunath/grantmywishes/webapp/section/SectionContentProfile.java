package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import org.jetbrains.annotations.NotNull;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentProfile extends SectionContent {

    public SectionContentProfile(final String id) {

        super( id );
    }

    public static class SectionStateProfile extends SectionState<SectionContentProfile> {

        public SectionStateProfile(final SectionContentProfile content) {

        }

        public SectionStateProfile(final String fragment) {

        }

        @Override
        public String toFragment() {

            return null;
        }

        @Override
        public void apply(@NotNull final SectionContentProfile panel)
                throws IncompatibleStateException {

        }
    }
}
