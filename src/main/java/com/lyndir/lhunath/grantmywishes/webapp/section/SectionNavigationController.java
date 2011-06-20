package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.opal.wayward.navigation.*;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.jetbrains.annotations.NotNull;


/**
 * <i>06 21, 2011</i>
 *
 * @author lhunath
 */
public class SectionNavigationController extends FragmentNavigationListener.Controller<SectionContent, SectionState> {

    private static final SectionNavigationController instance = new SectionNavigationController();

    private SectionNavigationController() {

    }

    public static SectionNavigationController get() {

        return instance;
    }

    @NotNull
    @Override
    protected Iterable<? extends FragmentNavigationTab<? extends SectionContent, ? extends SectionState>> getTabs() {

        return SectionInfo.sections;
    }

    @NotNull
    @Override
    protected <TT extends FragmentNavigationTab<PP, SS>, PP extends SectionContent, SS extends SectionState> PP getContent(
            @NotNull final TT tab) {

        return null;
    }

    @NotNull
    @Override
    protected Iterable<? extends Component> getNavigationComponents() {

        return null;
    }

    @Override
    protected <TT extends FragmentNavigationTab<?, ?>> void onTabActivated(@NotNull final TT tab, @NotNull final Panel tabPanel) {

    }

    @Override
    protected void onError(@NotNull final IncompatibleStateException e) {

    }
}
