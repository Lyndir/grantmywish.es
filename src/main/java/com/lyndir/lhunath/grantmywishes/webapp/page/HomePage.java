package com.lyndir.lhunath.grantmywishes.webapp.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


/**
 * <i>06 11, 2011</i>
 *
 * @author lhunath
 */
public class HomePage extends LayoutPage {

    @Override
    protected IModel<String> getPageTitle() {

        return Model.of("Home");
    }
}
