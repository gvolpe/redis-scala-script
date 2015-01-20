package script

import akka.actor.ActorSystem
import script.actors.{Start, MasterActor}

object ScriptApp extends App {

  println("Starting script within actor system...")

  val system = ActorSystem("redis-actor-system")
  val master = system.actorOf(MasterActor.props)

  master ! Start

  system.awaitTermination

}