package com.lyndir.lhunath.grantmywishes.webapp.section;

import com.lyndir.lhunath.opal.system.logging.Logger;
import com.lyndir.lhunath.opal.system.util.Throw;
import com.lyndir.lhunath.opal.wayward.navigation.TabDescriptor;
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
public enum SectionInfo implements TabDescriptor<SectionContent, SectionState<SectionContent>> {

    ABOUT(
            "about", null, "alnum information3-sc49", //
            SectionContentAbout.class, SectionContentAbout.SectionStateAbout.class ),
    SEARCH(
            "search", SectionToolSearch.class, "business magnifying-glass-ps", //
            SectionContentSearch.class, SectionContentSearch.SectionStateSearch.class ),
    RECORD(
            "record", SectionToolRecord.class, "people rocking-horse-sc44",  //
            SectionContentRecord.class, SectionContentRecord.SectionStateRecord.class ),
    PROFILE(
            "profile", SectionToolProfile.class, "people hand-left1-ps",  //
            SectionContentUser.class, SectionContentUser.SectionStateProfile.class ),
    SUPPORT(
            "support", SectionToolSupport.class, "people hat6-sc44",  //
            SectionContentSupport.class, SectionContentSupport.SectionStateSupport.class );

    static final Logger logger = Logger.get( SectionInfo.class );

    private final String                              id;
    private final Class<? extends WebMarkupContainer> toolPanel;
    private final Class<? extends SectionContent>     contentPanelClass;
    private final Class<? extends SectionState<?>>    stateClass;
    private final String                              toolItemSprite;

    <P extends SectionContent> SectionInfo(final String id, final Class<? extends WebMarkupContainer> toolPanel,
                                           final String toolItemSprite, final Class<P> contentPanelClass,
                                           final Class<? extends SectionState<P>> stateClass) {

        this.id = id;
        this.toolPanel = toolPanel;
        this.contentPanelClass = contentPanelClass;
        this.toolItemSprite = toolItemSprite;
        this.stateClass = stateClass;
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
    public SectionState<SectionContent> newState(@NotNull final String fragment) {

        try {
            return (SectionState<SectionContent>) stateClass.getConstructor( fragment.getClass() ).newInstance( fragment );
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
    public Class<? extends SectionContent> getContentPanelClass() {

        return contentPanelClass;
    }

    @Override
    public boolean shownInNavigation() {

        return true;
    }
}
