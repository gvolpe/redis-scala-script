package script.clients

import scredis._
import scala.util.{ Success, Failure }

object SCRedis {

  def runExample = runRedisFunction(redisHGetAllExample)

  def runUpdateScript = runRedisFunction(updateScript)

  // Generic function to open and close a Redis Connection and Execute a specific function
  private def runRedisFunction(executeFunction: Redis => _) = {

    // Creates a Redis instance with default configuration.
    // See reference.conf for the complete list of configurable parameters.
    val redis = Redis()

    // Import internal ActorSystem's dispatcher (execution context) to register callbacks
    import redis.dispatcher

    // Execute the specific function
    executeFunction(redis)

    // Shutdown all initialized internal clients along with the ActorSystem
    redis.get("whatever") onComplete { case _ => redis.quit }
  }

  private def redisHGetAllExample(redis: Redis) = {
    import redis.dispatcher

    // Executing a non-blocking command and registering callbacks on the returned Future
    redis.hGetAll("my-hash") onComplete {
      case Success(Some(value)) => println(value)
      case Success(None) => println("No existe valor para la clave...")
      case Failure(e) => e.printStackTrace()
    }
  }

  // Script masivo
  private def updateScript(redis: Redis) = {
    import redis.dispatcher

    redis.inTransaction { t =>
      t.set("a", "the a value")
      t.get("a")
    }.onComplete {
      case Success(value) => println(value)
      case Failure(e) => e.printStackTrace()
    }
  }

}