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

    redis.withTransaction { t =>
      massiveData(t)
      t.time
    }.onComplete {
      case Success(value) => println(value)
      case Failure(e) => e.printStackTrace()
    }
  }

  private def massiveData(t: TransactionBuilder) = {
    for (i <- 0 to 10000) {
      t.hSet("script:users:1", i.toString(), "value" + i)
      t.hSet("script:users:2", i.toString(), "value" + i)
    }
  }

  ////////////////////////////////////////////////////////////////

  def runUpdateScriptV2(x: Int) = {

    // Creates a Redis instance with default configuration.
    // See reference.conf for the complete list of configurable parameters.
    val redis = Redis()

    // Import internal ActorSystem's dispatcher (execution context) to register callbacks
    import redis.dispatcher

    // Execute the specific function
    updateScriptV2(redis, x)

    // Shutdown all initialized internal clients along with the ActorSystem
    redis.get("whatever") onComplete { case _ => redis.quit }
  }

  private def updateScriptV2(redis: Redis, x: Int) = {
    import redis.dispatcher

    redis.withTransaction { t =>
      massiveDataV2(t, x)
      t.time
    }.onComplete {
      case Success(value) => println(value)
      case Failure(e) => e.printStackTrace()
    }
  }

  private def massiveDataV2(t: TransactionBuilder, x: Int) = {
    for (i <- x to (x + 100)) {
      t.hSet("script:users:", i.toString(), "value" + i)
    }
  }

}