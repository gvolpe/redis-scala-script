redis-scala-script
==================

Ejemplo de uso de scredis (https://github.com/Livestream/scredis)

## How to run
* Clone this repo in your computer
* Go to the project folder
* Execute sbt run (assume that [sbt](http://www.scala-sbt.org/release/tutorial/Installing-sbt-on-Linux.html) is already installed)

## How to use in Eclipse IDE
* Create the file ~/.sbt/0.13/plugins/build.sbt if does not exists.
* Add the follow line to the build.sbt file and save it: ```addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.5.0") ```
* Now you can import the project from Eclipse.

Note: Redis connection configuration is located in src/main/resources/reference.conf
