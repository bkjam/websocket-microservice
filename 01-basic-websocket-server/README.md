# Building a Websocket Server

This section covers how to build a simple websocket server using Spring Boot and Redis Pub/Sub. In a microservice architecture,
the websocket server is the only microservice that establishes websocket connections with the web application (Frontend). It 
also acts as a middleware (or proxy) for real-time communications between the web application (Frontend) and other microservices 
(Backend). For more information, check out [this article](https://medium.com/@kbryan1008/building-a-websocket-server-in-a-microservice-architecture-50c6c6432e2b).

## Usage

1. Start the Redis Server

   ```bash
   docker run --name redis -p 6379:6379 -d redis:6.2.6
   ```
  
2. Start the Websocket Server

   ```bash
   ./gradlew bootrun
   ```

## Useful Commands

```bash
# POST Request
curl -X POST -d '{"topic": "/topic/toast", "message": "testing API endpoint" }' -H "Content-Type: application/json" localhost:8080/api/notification

# Redis CLI
docker exec -it redis redis-cli
PUBSUB CHANNELS
PUBLISH GREETING_CHANNEL_INBOUND "\"Test\""
SUBSCRIBE GREETING_CHANNEL_OUTBOUND
```
