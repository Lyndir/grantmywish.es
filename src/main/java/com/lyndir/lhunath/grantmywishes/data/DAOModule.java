/*
 *   Copyright 2010, Maarten Billemont
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.lyndir.lhunath.grantmywishes.data;

import com.db4o.ObjectContainer;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.lyndir.lhunath.grantmywishes.data.service.UserDAO;
import com.lyndir.lhunath.grantmywishes.data.service.WishDAO;
import com.lyndir.lhunath.grantmywishes.data.service.db4o.*;
import com.lyndir.lhunath.opal.system.logging.Logger;


/**
 * <h2>{@link DAOModule}<br> <sub>[in short] (TODO).</sub></h2>
 *
 * <p> [description / usage]. </p>
 *
 * <p> <i>Jan 9, 2010</i> </p>
 *
 * @author lhunath
 */
public class DAOModule extends AbstractModule {

    static final Logger logger = Logger.get( DAOModule.class );

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {

        // Services
        logger.dbg( "Binding data services" );
        bind( UserDAO.class ).to( UserDAOImpl.class );
        bind( WishDAO.class ).to( WishDAOImpl.class );

        // Database
        logger.dbg( "Binding persistence providers" );
        bind( ObjectContainer.class ).toProvider( DB4OProvider.class ).in( Scopes.SINGLETON );
    }
}
