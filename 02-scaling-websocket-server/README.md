# Horizontal Scaling the Websocket Server

This section covers how to scale the websocket server and resolve the message loss issue using publish-subscribe messaging
pattern. For more information, check out [this article]().

## Usage

1. Start the Redis Server
   
   ```bash
   # Start the Redis Server
   docker run --name redis -p 6379:6379 -d redis:6.2.6
   
   # Create Consumer Group for the Event Stream that we are tesing
   docker exec redis redis-cli XGROUP CREATE TEST_EVENT_TO_BACKEND CONSUMER_GROUP $ MKSTREAM
   ```

2. Start the Websocket Server

   ```bash
   ./gradlew websocket-server:bootrun
   ```

3. Start up 2 or more backend instances

   ```bash
   ./gradlew backend-service:bootrun -Pargs=--server.port=18080,--spring.application.name=backend-A
   ./gradlew backend-service:bootrun -Pargs=--server.port=28080,--spring.application.name=backend-B
   ...
   ```

4. Verify Redis Streams

   ```bash
   docker exec -it redis /bin/bash
   redis-cli
   xrange TEST_EVENT - +
   ```

5. Verify that only a single backend service received the message from websocket server (check the logs)


## Notable Issues

1. Redis NOGROUP error. You might hit the following issue where the consumer group is not created. To resolve, create the
consumer group using the redis command: `XGROUP CREATE my_stream my_group $ MKSTREAM`

    ```bash
    org.springframework.data.redis.RedisSystemException: Error in execution; nested exception is io.lettuce.core.RedisCommandExecutionException: NOGROUP No such key 'TEST_EVENT_TO_BACKEND' or consumer group 'MY-GROUP' in XREADGROUP with GROUP option
    ```

2. Deserializing _class issue. [https://github.com/spring-projects/spring-data-redis/issues/2251](https://github.com/spring-projects/spring-data-redis/issues/2251)

## Useful Commands

```bash
# Redis Stream Commands
xadd my_stream * key value key value ...
xrange my_stream - +                            # Show stream from beginning to end
XGROUP CREATE my_stream mygroup $
XINFO GROUPS <my_stream>
```

