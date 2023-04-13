FROM busybox:latest as build-stage
WORKDIR /work
RUN wget https://d1i4a15mxbxib1.cloudfront.net/api/plugins/confluentinc/kafka-connect-jdbc/versions/10.6.4/confluentinc-kafka-connect-jdbc-10.6.4.zip
RUN unzip confluentinc-kafka-connect-jdbc-10.6.4.zip

FROM confluentinc/cp-kafka-connect:7.3.2
COPY --from=build-stage /work/confluentinc-kafka-connect-jdbc-10.6.4/lib/* /usr/share/java/kafka-connect-jdbc/
