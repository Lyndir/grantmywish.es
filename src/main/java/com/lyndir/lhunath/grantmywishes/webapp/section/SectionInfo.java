package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.google.common.collect.ImmutableList;
import com.lyndir.lhunath.opal.system.logging.Logger;
import com.lyndir.lhunath.opal.system.util.Throw;
import com.lyndir.lhunath.opal.wayward.navigation.TabDescriptor;
import java.lang.reflect.InvocationTargetException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionInfo<P extends SectionContent, S extends SectionState<P>> implements TabDescriptor<P, S> {

    public static final SectionInfo<SectionContentAbout, SectionContentAbout.SectionStateAbout> ABOUT = of(
            "about", null, "alnum information3-sc49", //
            SectionContentAbout.class, SectionContentAbout.SectionStateAbout.class );

    public static final SectionInfo<SectionContentSearch, SectionContentSearch.SectionStateSearch> SEARCH = of(
            "search", SectionToolSearch.class, "business magnifying-glass-ps", //
            SectionContentSearch.class, SectionContentSearch.SectionStateSearch.class );

    public static final SectionInfo<SectionContentWishes, SectionContentWishes.SectionStateWishes> WISHES = of(
            "wishes", SectionToolWishes.class, "people rocking-horse-sc44",  //
            SectionContentWishes.class, SectionContentWishes.SectionStateWishes.class );

    public static final SectionInfo<SectionContentUser, SectionContentUser.SectionStateUser> USER = of(
            "user", SectionToolUser.class, "people hand-left1-ps",  //
            SectionContentUser.class, SectionContentUser.SectionStateUser.class );

    public static final SectionInfo<SectionContentSupport, SectionContentSupport.SectionStateSupport> SUPPORT = of(
            "support", SectionToolSupport.class, "people hat6-sc44",  //
            SectionContentSupport.class, SectionContentSupport.SectionStateSupport.class );

    private static final ImmutableList<SectionInfo<SectionContent, SectionState<SectionContent>>> TABS;
    static {
        ImmutableList.Builder<SectionInfo<SectionContent, SectionState<SectionContent>>> builder = ImmutableList.builder();
        builder.add( (SectionInfo) ABOUT );
        builder.add( (SectionInfo) SEARCH );
        builder.add( (SectionInfo) WISHES );
        builder.add( (SectionInfo) USER );
        builder.add( (SectionInfo) SUPPORT );
        TABS = builder.build();
    }

    static final Logger logger = Logger.get( SectionInfo.class );

    private final String                          id;
    private final Class<? extends SectionTool<P>> toolPanel;
    private final Class<P>                        contentPanelClass;
    private final Class<S>                        stateClass;
    private final String                          toolItemSprite;

    SectionInfo(final String id, final Class<? extends SectionTool<P>> toolPanel, final String toolItemSprite,
                final Class<P> contentPanelClass, final Class<S> stateClass) {

        this.id = id;
        this.toolPanel = toolPanel;
        this.contentPanelClass = contentPanelClass;
        this.toolItemSprite = toolItemSprite;
        this.stateClass = stateClass;
    }

    private static <P extends SectionContent, S extends SectionState<P>> SectionInfo<P, S> of(final String id,
                                                                                              final Class<? extends SectionTool<P>> toolPanel,
                                                                                              final String toolItemSprite,
                                                                                              final Class<P> contentPanelClass,
                                                                                              final Class<S> stateClass) {

        return new SectionInfo<P, S>( id, toolPanel, toolItemSprite, contentPanelClass, stateClass );
    }

    public static ImmutableList<SectionInfo<SectionContent, SectionState<SectionContent>>> values() {

        return TABS;
    }

    public String getId() {

        return id;
    }

    public SectionTool<?> getToolPanel(final String wicketId) {

        if (toolPanel == null)
            return new SectionTool<SectionContent>( wicketId ) {
                @Override
                protected void onConfigure() {

                    super.onConfigure();

                    setVisible( false );
                }
            };

        try {
            return toolPanel.getConstructor( String.class ).newInstance( wicketId );
        }
        catch (InstantiationException e) {
            throw logger.bug( e );
        }
        catch (IllegalAccessException e) {
            throw logger.bug( e );
        }
        catch (InvocationTargetException e) {
            throw logger.bug( e );
        }
        catch (NoSuchMethodException e) {
            throw logger.bug( e );
        }
    }

    public SectionContent getContentPanel(final String wicketId) {

        if (contentPanelClass == null)
            return new SectionContent( wicketId ) {
                @Override
                protected void onConfigure() {

                    super.onConfigure();

                    setVisible( false );
                }
            };

        try {
            SectionContent contentPanel = contentPanelClass.getConstructor( String.class ).newInstance( wicketId );
            contentPanel.setMarkupId( String.format( "%s-content", getId() ) ).setOutputMarkupId( true );
            SectionNavigationController.get().registerSection( this, contentPanel );

            return contentPanel;
        }
        catch (InstantiationException e) {
            throw logger.bug( e );
        }
        catch (IllegalAccessException e) {
            throw logger.bug( e );
        }
        catch (InvocationTargetException e) {
            throw logger.bug( e );
        }
        catch (NoSuchMethodException e) {
            throw logger.bug( e );
        }
    }

    public String getToolItemSprite() {

        return toolItemSprite;
    }

    @NotNull
    @Override
    public String getFragment() {

        return getId();
    }

    @NotNull
    @Override
    @SuppressWarnings({ "unchecked" })
    public SectionState<SectionContent> newState(@NotNull final SectionContent panel) {

        try {
            return (SectionState<SectionContent>) stateClass.getConstructor( panel.getClass() ).newInstance( panel );
        }
        catch (InstantiationException e) {
            throw logger.bug( e );
        }
        catch (IllegalAccessException e) {
            throw logger.bug( e );
        }
        catch (InvocationTargetException e) {
            throw Throw.propagate( e );
        }
        catch (NoSuchMethodException e) {
            throw logger.bug( e );
        }
    }

    @NotNull
    @Override
    @SuppressWarnings({ "unchecked" })
    public S newState(@NotNull final String fragment) {

        try {
            return stateClass.getConstructor( fragment.getClass() ).newInstance( fragment );
        }
        catch (InstantiationException e) {
            throw logger.bug( e );
        }
        catch (IllegalAccessException e) {
            throw logger.bug( e );
        }
        catch (InvocationTargetException e) {
            throw Throw.propagate( e );
        }
        catch (NoSuchMethodException e) {
            throw logger.bug( e );
        }
    }

    @NotNull
    @Override
    public IModel<String> getTitle() {

        return Model.of();
    }

    @NotNull
    @Override
    public Class<P> getContentPanelClass() {

        return contentPanelClass;
    }

    @Override
    public boolean shownInNavigation() {

        return true;
    }
}
