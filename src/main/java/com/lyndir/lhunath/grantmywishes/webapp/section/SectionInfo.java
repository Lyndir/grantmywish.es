package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.google.common.collect.ImmutableList;
import com.lyndir.lhunath.opal.system.logging.Logger;
import com.lyndir.lhunath.opal.wayward.navigation.FragmentNavigationTab;
import com.lyndir.lhunath.opal.wayward.navigation.IncompatibleStateException;
import java.lang.reflect.InvocationTargetException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.jetbrains.annotations.NotNull;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public class SectionInfo<P extends SectionContent, S extends SectionState> implements FragmentNavigationTab<P, S> {

    public static final SectionInfo<SectionContentAbout, SectionContentAbout.SectionStateAbout>       ABOUT    = new SectionInfo<SectionContentAbout, SectionContentAbout.SectionStateAbout>(
            "about", null, "alnum information3-sc49", SectionContentAbout.class );
    public static final SectionInfo<SectionContentSearch, SectionContentSearch.SectionStateSearch>    SEARCH   = new SectionInfo<SectionContentSearch, SectionContentSearch.SectionStateSearch>(
            "search", SectionToolSearch.class, "business magnifying-glass-ps", SectionContentSearch.class );
    public static final SectionInfo<SectionContentRecord, SectionContentRecord.SectionStateRecord>    RECORD   = new SectionInfo<SectionContentRecord, SectionContentRecord.SectionStateRecord>(
            "record", SectionToolRecord.class, "people rocking-horse-sc44", SectionContentRecord.class );
    public static final SectionInfo<SectionContentProfile, SectionContentProfile.SectionStateProfile> PROFILE  = new SectionInfo<SectionContentProfile, SectionContentProfile.SectionStateProfile>(
            "profile", SectionToolProfile.class, "people hand-left1-ps", SectionContentProfile.class );
    public static final SectionInfo<SectionContentSupport, SectionContentSupport.SectionStateSupport> SUPPORT  = new SectionInfo<SectionContentSupport, SectionContentSupport.SectionStateSupport>(
            "support", SectionToolSupport.class, "people hat6-sc44", SectionContentSupport.class );
    public static final ImmutableList<SectionInfo<?, ?>>                                              sections = ImmutableList.<SectionInfo<?, ?>>of(
            ABOUT, SEARCH, RECORD, PROFILE, SUPPORT );

    static final Logger logger = Logger.get( SectionInfo.class );

    private final String                              id;
    private final Class<? extends WebMarkupContainer> toolPanel;
    private final Class<P>                            contentPanelClass;
    private final String                              toolItemSprite;

    SectionInfo(final String id, final Class<? extends WebMarkupContainer> toolPanel, final String toolItemSprite,
                final Class<P> contentPanelClass) {

        this.id = id;
        this.toolPanel = toolPanel;
        this.contentPanelClass = contentPanelClass;
        this.toolItemSprite = toolItemSprite;
    }

    public String getId() {

        return id;
    }

    public WebMarkupContainer getToolPanel(final String wicketId) {

        if (toolPanel == null)
            return new WebMarkupContainer( wicketId ) {
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

    public WebMarkupContainer getContentPanel(final String wicketId) {

        if (contentPanelClass == null)
            return new WebMarkupContainer( wicketId ) {
                @Override
                protected void onConfigure() {

                    super.onConfigure();

                    setVisible( false );
                }
            };

        try {
            WebMarkupContainer contentPanel = contentPanelClass.getConstructor( String.class ).newInstance( wicketId );
            contentPanel.setMarkupId( String.format( "%s-content", getId() ) ).setOutputMarkupId( true );

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
    public String getTabFragment() {

        return getId();
    }

    @NotNull
    @Override
    public S buildFragmentState(@NotNull final SectionContent panel) {

        return null;
    }

    @Override
    public void applyFragmentState(@NotNull final SectionContent panel, @NotNull final SectionState state)
            throws IncompatibleStateException {

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
    public boolean isInNavigation() {

        return false;
    }

    @NotNull
    @Override
    public S getState(@NotNull final String fragment) {

        return null;
    }
}
