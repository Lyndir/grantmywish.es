package com.lyndir.lhunath.grantmywishes.webapp.section;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.lyndir.lhunath.opal.wayward.navigation.*;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.panel.Panel;
import org.jetbrains.annotations.NotNull;


/**
 * <i>06 21, 2011</i>
 *
 * @author lhunath
 */
public class SectionNavigationController extends TabController {

    private static final SectionNavigationController instance = new SectionNavigationController();
    private static final WeakHashMap<Session, Map<SectionInfo, Panel>> tabToPanel = new WeakHashMap<Session, Map<SectionInfo, Panel>>();

    private SectionNavigationController() {

    }

    public static SectionNavigationController get() {

        return instance;
    }

    public void registerSection(final SectionInfo info, final Panel content) {

        Map<SectionInfo, Panel> sessionMap = tabToPanel.get( Session.get() );
        if (sessionMap == null)
            tabToPanel.put( Session.get(), sessionMap = Maps.newHashMap() );

        sessionMap.put( info, content );
    }

    @NotNull
    @Override
    protected Iterable<? extends TabDescriptor<SectionContent, SectionState<SectionContent>>> getTabs() {

        return ImmutableList.copyOf( SectionInfo.values() );
    }

    @NotNull
    @Override
    @SuppressWarnings({ "unchecked", "SuspiciousMethodCalls" })
    protected <T extends TabDescriptor<P, ?>, P extends Panel> P getContent(@NotNull final T tab) {

        return (P) checkNotNull(tabToPanel.get( Session.get() )).get( tab );
    }

    @NotNull
    @Override
    protected Iterable<? extends Component> getNavigationComponents() {

        return ImmutableList.of();
    }

    @Override
    protected <T extends TabDescriptor<P, ?>, P extends Panel> void onTabActivated(@NotNull final T tab, @NotNull final P tabPanel) {

    }

    @Override
    protected void onError(@NotNull final IncompatibleStateException e) {

    }
}
