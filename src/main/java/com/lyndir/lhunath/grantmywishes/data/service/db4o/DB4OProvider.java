package com.lyndir.lhunath.grantmywishes.data.service.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.config.QueryEvaluationMode;
import com.db4o.ta.TransparentPersistenceSupport;
import com.google.inject.Provider;
import com.lyndir.lhunath.grantmywishes.data.User;


/**
 * <h2>{@link DB4OProvider}<br> <sub>[in short] (TODO).</sub></h2>
 *
 * <p> <i>07 09, 2010</i> </p>
 *
 * @author lhunath
 */
public class DB4OProvider implements Provider<ObjectContainer> {

    @Override
    public ObjectContainer get() {

        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        // TODO: Figure out why transparent persistence doesn't work with S3MediaData's map.
        configuration.common().add( new TransparentPersistenceSupport() );
        configuration.common().updateDepth( 5 );
        configuration.common().queries().evaluationMode( QueryEvaluationMode.LAZY );
        // TODO: Do this smarter; annotations or such.
        configuration.common().objectClass( User.class ).objectField( "name" ).indexed( true );
        // TODO: NQ optimization isn't working.  Fix it or convert to SODA style or find a way to do better SODA through annotations.
        //        configuration.common().diagnostic().addListener( new DiagnosticListener() {
        //
        //            @Override
        //            public void onDiagnostic(final Diagnostic diagnostic) {
        //                logger.dbg( "[DB4O-DIAG] %s", diagnostic );
        //            }
        //        } );

        return Db4oEmbedded.openFile( configuration, "grantmywishes.db4o" );
    }
}
