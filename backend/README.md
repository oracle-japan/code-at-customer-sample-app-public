# Backend

## Running in local machine

`application-local.yaml` を作成します。

```yaml
spring:
  datasource:
    url: <ATP との接続 URL>
    username: <ATP のユーザ名>
    password: <ATP のパスワード>
    driver-class-name: oracle.jdbc.OracleDriver
    oracleucp:
      connection-factory-class-name: oracle.jdbc.replay.OracleDataSourceImpl
      sql-for-validate-connection: select * from dual
      connection-pool-name: JDBC_UCP_POOL
      initial-pool-size: 1
      min-pool-size: 1
      max-pool-size: 1
      inactive-connection-timeout: 10
      query-timeout: 600
      fast-connection-failover-enabled: false
```

以下コマンドで実行します。

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## Build

```bash
./mvnw spring-boot:build-image -DskipTests
```
