# frontend app

```
./build.sh

docker run --rm -it -p 8080:8080 --name front-app \
    -e oauth.url=$OAUTH_URL \
    -e oauth.client-id=$OAUTH_CLIENT_ID \
    -e oauth.client-secret=$OAUTH_CLIENT_SECRET \
    -e oauth.scope=$OAUTH_SCOPE \
    -e oauth.cache-token=$OAUTH_CACHE_TOKEN \
    -e backend.url.base=$BACKEND_URL_BASE \
    frontend:0.0.1
```
oauth.cache-token is optional, default = true.

expected APIs:
+ $BACKEND_URL_BASE/items
+ $BACKEND_URL_BASE/items/{id}
