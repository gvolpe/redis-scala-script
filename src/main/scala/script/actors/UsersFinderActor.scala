package script.actors

import akka.actor.{Actor, Props}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scredis._
import script.redis.RedisConnectionManager

case object FindAll
case class Users(users: Option[Map[String, String]])

object UsersFinderActor {

  def props = Props(new UsersFinderActor)

}

class UsersFinderActor extends Actor {

  val redis = RedisConnectionManager.connection

  def findAll: Future[Option[Map[String, String]]] = {
    redis.hGetAll("script:users:1")
  }

  def receive = {
    case FindAll =>
      val master = sender
      findAll.map { users =>
        RedisConnectionManager.closeConnection(redis)
        master ! users
      }
  }

}