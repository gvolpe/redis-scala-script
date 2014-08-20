package script

import script.clients.SCRedis

object ScriptApp {

  def main(args: Array[String]): Unit = {
    println("Ejecutando Redis Example...")
    SCRedis.runExample

    println("Ejecutando Redis Update Script...")
    SCRedis.runUpdateScript
  }

}