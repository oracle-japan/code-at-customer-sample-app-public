FROM node:18-alpine as build-vue
ARG APM_PUBLIC_DATA_KEY
ARG APM_DATA_UPLOAD_ENDPOINT
WORKDIR /frontend
COPY front-vue .
# RUN sed -i -e "s@__APM_DATA_UPLOAD_ENDPOINT__@${APM_DATA_UPLOAD_ENDPOINT}@g" public/index.html
# RUN sed -i -e "s@__APM_DATA_UPLOAD_ENDPOINT__@${APM_DATA_UPLOAD_ENDPOINT}@g" public/index.html
# RUN sed -i -e "s/__APM_PUBLIC_DATA_KEY__/${APM_PUBLIC_DATA_KEY}/g" public/index.html
RUN npm install && npm run build

FROM eclipse-temurin:17-jdk-alpine as build-spring
ARG APM_JAVA_VERSION
WORKDIR /frontend
COPY front-spring .
COPY --from=build-vue /frontend/dist src/main/resources/static/
RUN ./mvnw package -DskipTests
# COPY apm/*.jar .
# RUN mkdir apm && java -jar ./apm-java-agent-installer-${APM_JAVA_VERSION}.jar provision-agent \
#   -service-name=overwritebyenv \
#   -destination=./apm \
#   -private-data-key=overwritebyenv \
#   -data-upload-endpoint=https://overwritebyenv

FROM eclipse-temurin:17-jre-alpine
WORKDIR /frontend
COPY --from=build-spring /frontend/target/frontend.jar .
# COPY --from=build-spring /frontend/apm ./apm
EXPOSE 8080
# ENTRYPOINT ["java", "-javaagent:/frontend/apm/oracle-apm-agent/bootstrap/ApmAgent.jar", "-jar", "/frontend/frontend.jar"]
ENTRYPOINT ["java", "-jar", "/frontend/frontend.jar"]
