package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import org.jetbrains.annotations.NotNull;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentRecord extends SectionContent {

    public SectionContentRecord(final String id) {

        super( id );
    }

    public static class SectionStateRecord extends SectionState<SectionContentRecord> {

        public SectionStateRecord(final SectionContentRecord content) {

        }

        public SectionStateRecord(final String fragment) {

        }

        @Override
        public String toFragment() {

            return null;
        }

        @Override
        public void apply(@NotNull final SectionContentRecord panel)
                throws IncompatibleStateException {

        }
    }
}
