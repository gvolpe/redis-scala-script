package script

import akka.actor.ActorSystem
import akka.actor.Props
import script.actors.FindUserActor

object ScriptAppWithActors {

  def main(args: Array[String]): Unit = {
    println("Iniciando script con sistema de actores...")

    val system = ActorSystem("redis-actor-system")
    val usersActor = system.actorOf(Props[FindUserActor])

    usersActor ! "ActorSystem"

    system.shutdown
    system.awaitTermination
  }

}