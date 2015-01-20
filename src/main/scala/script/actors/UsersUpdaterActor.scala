package script.actors

import akka.actor.{Actor, Props}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import script.redis.RedisConnectionManager

case class Update(users: Map[String, String])

object UsersUpdaterActor {

  def props = Props(new UsersUpdaterActor)

}

class UsersUpdaterActor extends Actor {

  val redis = RedisConnectionManager.connection

  def updateUsers(users: Map[String, String]): Future[(Long, Long)] = {
    redis.withTransaction { t =>
      users.foreach(u => t.hSet("script:users:1", u._1, u._2 + "*"))
      t.time
    }
  }

  def receive = {
    case Update(users) =>
      val master = sender
      updateUsers(users).map { result =>
        RedisConnectionManager.closeConnection(redis)
        master ! FinishUpdate
      }
  }

}