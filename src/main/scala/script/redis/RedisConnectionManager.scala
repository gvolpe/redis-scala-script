package script.redis

import scredis._
import scala.concurrent.ExecutionContext.Implicits.global

object RedisConnectionManager {

  // Creates a Redis instance with default configuration.
  // See reference.conf for the complete list of configurable parameters.
  def connection: Redis = Redis()

  def closeConnection(redis: Redis): Unit = {
    // Shutdown all initialized internal clients along with the ActorSystem
    redis.get("whatever") map { _ => redis.quit }
  }

}
