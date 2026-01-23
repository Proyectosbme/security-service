package security.framework.config;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.PreMatching;

import java.io.IOException;
import java.util.Set;

/**
 * Filtro CORS global para asegurar cabeceras incluso en respuestas de error.
 */
@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Set<String> ALLOWED_ORIGINS = Set.of(
            "http://localhost:4200",
            "http://127.0.0.1:4200",
            "http://192.168.1.132:4200"
    );

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            String origin = getHeader(requestContext, "Origin");
            if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
                Response.ResponseBuilder builder = Response.ok();
                addCorsHeaders(builder, origin, getHeader(requestContext, "Access-Control-Request-Headers"));
                requestContext.abortWith(builder.build());
            } else {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String origin = getHeader(requestContext, "Origin");
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            addCorsHeaders(responseContext.getHeaders(), origin, getHeader(requestContext, "Access-Control-Request-Headers"));
        }
    }

    private void addCorsHeaders(MultivaluedMap<String, Object> headers, String origin, String requestHeaders) {
        headers.putSingle("Access-Control-Allow-Origin", origin);
        headers.putSingle("Access-Control-Allow-Credentials", "true");
        headers.putSingle("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
        headers.putSingle("Access-Control-Allow-Headers", requestHeaders != null ? requestHeaders : "*");
        headers.putSingle("Access-Control-Max-Age", "3600");
    }

    private void addCorsHeaders(Response.ResponseBuilder builder, String origin, String requestHeaders) {
        builder.header("Access-Control-Allow-Origin", origin);
        builder.header("Access-Control-Allow-Credentials", "true");
        builder.header("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
        builder.header("Access-Control-Allow-Headers", requestHeaders != null ? requestHeaders : "*");
        builder.header("Access-Control-Max-Age", "3600");
    }

    private String getHeader(ContainerRequestContext requestContext, String name) {
        return requestContext.getHeaders().getFirst(name);
    }
}
