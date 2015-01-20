package script.actors

import akka.actor.{Actor, Props}

case object Start
case object FinishUpdate

object MasterActor {

  def props = Props(new MasterActor)

}

class MasterActor extends Actor {

  def updateUsers(foundUsers: Option[Map[String, String]]) = foundUsers match {
    case Some(users) =>
      println("user 1 >> " + users.get("1"))
      val updater = context.actorOf(UsersUpdaterActor.props)
      updater ! Update(users)
    case None => println("No users found...")
  }

  def receive = {
    case Start =>
      val finder = context.actorOf(UsersFinderActor.props)
      finder ! FindAll
    case foundUsers: Option[Map[String, String]] =>
      updateUsers(foundUsers)
    case FinishUpdate =>
      println("Update finished...")
      context.system.shutdown
  }

}