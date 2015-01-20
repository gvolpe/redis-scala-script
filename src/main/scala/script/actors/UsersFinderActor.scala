package script.actors

import akka.actor.{Actor, Props}
import scredis._
import script.redis.RedisConnectionManager
import script.redis.RedisKeys.UserKeys

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case object FindAll
case class Users(users: Option[Map[String, String]])

object UsersFinderActor {

  def props = Props(new UsersFinderActor)

}

class UsersFinderActor extends Actor with UserKeys {

  val redis = RedisConnectionManager.connection

  def findAll: Future[Option[Map[String, String]]] = {
    redis.hGetAll(MAIN)
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