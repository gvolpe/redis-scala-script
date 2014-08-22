package script.actors

import akka.actor.Actor
import akka.actor.Props

class FindUserActor extends Actor {

  def receive = {
    case msj: String =>
      println("Buscando usuarios. Invoked by: " + msj)
      val updateUserActor = context.actorOf(Props[UpdateUserActor])
      updateUserActor ! "FindUserActor"
    case _ => println("Default message FindUserActor...")
  }

}