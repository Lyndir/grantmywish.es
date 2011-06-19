package com.lyndir.lhunath.grantmywishes.webapp.page;

import com.lyndir.lhunath.opal.system.logging.Logger;
import java.lang.reflect.InvocationTargetException;
import org.apache.wicket.markup.html.WebMarkupContainer;


/**
 * <i>06 19, 2011</i>
 *
 * @author lhunath
 */
public enum SectionInfo {

    ABOUT( null, "alnum information3-sc49" ),
    SEARCH( SearchSectionTool.class, "business magnifying-glass-ps" ),
    RECORD( RecordSectionTool.class, "people rocking-horse-sc44" ),
    PROFILE( ProfileSectionTool.class, "people hand-left1-ps" ),
    SUPPORT( SupportSectionTool.class, "people hat6-sc44" );

    static final Logger logger = Logger.get( SectionInfo.class );

    private final Class<? extends WebMarkupContainer> toolPanel;
    private final String                              toolItemSprite;

    SectionInfo(final Class<? extends WebMarkupContainer> toolPanel, final String toolItemSprite) {

        this.toolPanel = toolPanel;
        this.toolItemSprite = toolItemSprite;
    }

    public String getId() {

        return name().toLowerCase();
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

    public String getToolItemSprite() {

        return toolItemSprite;
    }
}
