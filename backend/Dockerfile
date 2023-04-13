FROM eclipse-temurin:17-jdk-alpine as build
ARG APM_JAVA_VERSION
WORKDIR /backend
COPY . .
RUN ./mvnw package -DskipTests
COPY apm-installer/*.jar .
# RUN mkdir apm && java -jar ./apm-java-agent-installer-${APM_JAVA_VERSION}.jar provision-agent \
#   -service-name=overwritebyenv \
#   -destination=./apm \
#   -private-data-key=overwritebyenv \
#   -data-upload-endpoint=https://overwritebyenv

FROM eclipse-temurin:17-jre-alpine
WORKDIR /backend
COPY --from=build /backend/target/backend-0.0.1.jar .
# COPY --from=build /backend/apm ./apm
EXPOSE 8080
# ENTRYPOINT ["java", "-javaagent:/backend/apm/oracle-apm-agent/bootstrap/ApmAgent.jar", "-jar", "/backend/backend-0.0.1.jar"]
ENTRYPOINT ["java", "-jar", "/backend/backend-0.0.1.jar"]