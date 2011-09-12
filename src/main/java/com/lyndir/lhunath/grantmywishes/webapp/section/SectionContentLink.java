package com.lyndir.lhunath.grantmywishes.webapp.section;

import org.apache.wicket.ajax.AjaxRequestTarget;


/**
 * <i>09 10, 2011</i>
 *
 * @author lhunath
 */
public interface SectionContentLink {

    String getTitle();

    void onClick(AjaxRequestTarget target);
}
