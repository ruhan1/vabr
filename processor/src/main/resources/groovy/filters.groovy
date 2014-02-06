package ${pkg};

import org.commonjava.vertx.vabr.ApplicationRouter;
import org.commonjava.vertx.vabr.types.Method;
import org.commonjava.vertx.vabr.filter.ExecutionChain;
import org.commonjava.vertx.vabr.filter.FilterBinding;
import org.commonjava.vertx.vabr.filter.AbstractFilterCollection;

import org.vertx.java.core.http.HttpServerRequest;

import org.commonjava.util.logging.Logger;
<%if( qualifier ){ %>
import ${qualifier.fullName};

@${qualifier.simpleName}<% } %>
public final class ${className}
    extends AbstractFilterCollection
{

    private final Logger logger = new Logger( getClass() );

    public ${className}()
    {<% templates.each { %>
        bind( new FilterBinding( ${it.priority}, "${it.httpPath}", Method.${it.httpMethod}, "${it.handlerKey}" )
        {
            public void dispatch( ApplicationRouter router, HttpServerRequest req, ExecutionChain chain )
                throws Exception
            {
                ${it.qualifiedClassname} handler = router.getResourceInstance( ${it.qualifiedClassname}.class );
                if ( handler != null )
                {
                    logger.debug( "Filtering via: %s", handler );
                    handler.${it.methodname}( req, chain );
                }
                else
                {
                    throw new RuntimeException( "Cannot retrieve handler instance for: " + toString() );
                } 
            }
        } );
        <% } %>
    }

}