package script.actors

import akka.actor.Actor
import akka.actor.Props
import script.clients.SCRedis

class UpdateUserActor extends Actor {

  val numberOfRedisCalls = 100

  def receive = {
    //    case x: Int =>
    //      SCRedis.runUpdateScriptV2(x)
    //      var calls = x + 100
    //      println("value of calls: " + calls)
    //      if (calls >= 500) sender ! AnyRef
    //      else self ! calls
    //    case msj: String =>
    //      println("Actualizando usuarios. Invoked by: " + msj)
    //      self ! 1
    case msj: String =>
      println("Actualizando usuarios. Invoked by: " + msj)
      sender ! AnyRef
    case _ => println("Default message UpdateUserActor...")
  }

}