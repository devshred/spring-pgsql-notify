# Asynchronous messaging with Spring Integration and PostgresSubscribableChannel
Since version 6 [Spring Integration](https://spring.io/projects/spring-integration) offers [receiving push notifications](https://docs.spring.io/spring-integration/docs/current/reference/html/jdbc.html#postgresql-push) using PostreSQLs [NOTIFY](https://www.postgresql.org/docs/current/sql-notify.html).
That can be used to build database-based asynchronous messaging.

## How-to start
### prepare
```shell
docker-compose up -d
mvn clean install
```

### start app
… start multiple instances (vary server port)
```shell
java -jar target/spring-integration-0.0.1-SNAPSHOT.jar --server.port=8081
```

### trigger event
```shell
http :8081/event/next
```