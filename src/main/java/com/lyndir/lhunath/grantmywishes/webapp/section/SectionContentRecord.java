package com.lyndir.lhunath.grantmywishes.webapp.section;

/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionContentRecord extends SectionContent {

    public SectionContentRecord(final String id) {

        super( id );
    }

    public class SectionStateRecord implements SectionState {

        @Override
        public String toFragment() {

            return null;
        }
    }
}
