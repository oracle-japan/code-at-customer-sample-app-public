package com.example.demo.front;

import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Base64;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class ItemsController {

    private final static Logger logger = LoggerFactory.getLogger(ItemsController.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate;

    @Value("${backend.url:}")
    private String backendUrl;

    @Value("${backend.url.base:}")
    private String backendUrlBase;

    @Value("${oauth.url}")
    private String oauthUrl;

    @Value("${oauth.client-id}")
    private String clientId;

    @Value("${oauth.client-secret}")
    private String clientSecret;

    @Value("${oauth.scope}")
    private String scope;

    @Value("${oauth.cache-token:true}")
    private boolean isTokenCacheEnabled;

    public ItemsController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @CrossOrigin
    @RequestMapping(value = "/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getItems() {
        String items = getItemsFromRemote(null, getToken()).getBody();
        logger.info("items:\n" + items);
        return items;
    }

    @CrossOrigin
    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getItem(@PathVariable Integer id) {
        String item = getItemsFromRemote(id, getToken()).getBody();
        logger.info("item: " + item);
        return item;
    }

    private String accessToken = null;
    private long expiresAt = -1;

    private String getToken() {
        //logger.info("oauthUrl: " + oauthUrl);
        //logger.info("clientId: " + clientId);
        //logger.info("clientSecret: " + clientSecret);
        //logger.info("scope: " + scope);

        long now = System.currentTimeMillis();
        //logger.info("Now is " + now);
        if(isTokenCacheEnabled && (expiresAt - now > (3 * 60 * 1000)) ){ // won't expire in three minutes
            logger.info(String.format("Token expires in %d seconds.", (expiresAt-now)/1000));
            return accessToken;
        }
        logger.info("Getting a new token.");
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        if(Objects.nonNull(scope)){
            map.add("scope", scope);
        }
        String userPassword = new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()));
        StringBuilder basicAuth = new StringBuilder();
        basicAuth.append("Basic ").append(userPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", basicAuth.toString());

        RequestEntity<?> request = RequestEntity.post(URI.create(oauthUrl))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .headers(headers)
                .body(map);

        String response = restTemplate.exchange(request, String.class).getBody();
        //logger.info("token:\n" + response);
        //return getValueFromJasonString(response, "access_token");
        JsonNode jsonNode = getJsonNode(response);
        accessToken = jsonNode.get("access_token").asText();
        if(isTokenCacheEnabled){
            long expiresIn = jsonNode.get("expires_in").asLong();
            expiresAt = System.currentTimeMillis() + (expiresIn * 1000);
            logger.info("Token expires at " + OffsetDateTime.ofInstant(Instant.ofEpochMilli(expiresAt/1000*1000), ZoneId.of("+09:00")).toString());
        }
        return accessToken;
    }

    private ResponseEntity<String> getItemsFromRemote(Integer id, String token) {

        StringBuilder auth = new StringBuilder();
        auth.append("Bearer ").append(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", auth.toString());

        String baseUrl = backendUrlBase;
        if(baseUrl.equals("")){ // backward compatibility
            baseUrl = backendUrl;
            if(baseUrl.endsWith("/")){
                baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
            }
            baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
        }

        String url = baseUrl.concat(baseUrl.endsWith("/") ? "" : "/")
                        .concat("items")
                        .concat(Objects.isNull(id) ? "" : "/".concat(id.toString()));
        RequestEntity<?> request = RequestEntity.get(URI.create(url))
                .headers(headers)
                .build();
        return restTemplate.exchange(request, String.class);
    }

    private JsonNode getJsonNode(String str){
        try{
            return mapper.readTree(str);
        }catch(Exception e){
            throw new RuntimeException("Error while processing json string - " + e.getMessage());
        }
    }

}