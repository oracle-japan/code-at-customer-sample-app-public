
package com.example.demo.helidonfront;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/")
@ApplicationScoped
public class ItemsResource {
    private final static Logger logger = Logger.getLogger(ItemsResource.class.getName());

    private final Client client = ClientBuilder.newBuilder().connectTimeout(5, TimeUnit.SECONDS).build();

    @Inject @ConfigProperty(name = "backend.url", defaultValue = "?")
    private String backendUrl;

    @Inject @ConfigProperty(name = "backend.url.base", defaultValue = "?")
    private String backendUrlBase;

    @Inject @ConfigProperty(name = "oauth.url")
    private String oauthUrl;

    @Inject @ConfigProperty(name = "oauth.client-id")
    private String clientId;

    @Inject @ConfigProperty(name = "oauth.client-secret")
    private String clientSecret;

    @Inject @ConfigProperty(name = "oauth.scope")
    private String scope;

    @Inject @ConfigProperty(name = "oauth.cache-token", defaultValue = "true")
    private boolean isTokenCacheEnabled;


    @GET @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItems() {

        String items = getItemsFromRemote(getToken(), null);
        logger.info("items:\n" + items);
        return items;
    }

    @GET @Path("/items/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItem(@PathParam("id") Integer id) {

        String items = getItemsFromRemote(getToken(), id);
        logger.info("items:\n" + items);
        return items;
    }


    private String accessToken = null;
    private long expiresAt = -1;

    private String getToken() {
        long now = System.currentTimeMillis();
        //logger.info("Now is " + now);
        if(isTokenCacheEnabled && (expiresAt - now > (3 * 60 * 1000)) ){ // won't expire in three minutes
            logger.info(String.format("Token expires in %d seconds.", (expiresAt-now)/1000));
            return accessToken;
        }
        logger.info("Getting a new token.");

        Builder builder = client.target(oauthUrl).request();
        String userPassword = new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()));
        StringBuilder basicAuth = new StringBuilder();
        basicAuth.append("Basic ").append(userPassword);
        builder.header("Authorization", basicAuth.toString());
        Form input = new Form();
        input.param("grant_type", "client_credentials");
        if(Objects.nonNull(scope)){
            input.param("scope", scope);
        }
        Entity<Form> entity = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);
        Response response = builder.post(entity);
        JsonObject json = response.readEntity(JsonObject.class);
        accessToken = json.getString("access_token");
        if(isTokenCacheEnabled){
            long expiresIn = json.getInt("expires_in");
            expiresAt = System.currentTimeMillis() + (expiresIn * 1000);
            logger.info("Token expires at " + OffsetDateTime.ofInstant(Instant.ofEpochMilli(expiresAt/1000*1000), ZoneId.of("+09:00")).toString());
        }
        return accessToken;
    }

    private String getItemsFromRemote(String token, Integer id) {
        
        String baseUrl = backendUrlBase;
        if(baseUrl.equals("?")){ // backward compatibility
            baseUrl = backendUrl;
            if(baseUrl.endsWith("/")){
                baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
            }
            baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
        }

        WebTarget webTarget = client.target(baseUrl).path("items");
        if(Objects.nonNull(id)){
            webTarget = webTarget.path(id.toString());
        }
        Builder builder = webTarget.request();
        builder.header("Authorization", "Bearer" + token);
        Response response = builder.get();
        return response.readEntity(String.class);
    }

}
