organization := "ar.com.gmvsoft"

name := "redis-scala-script"

version := "0.0.1"

scalaVersion := "2.11.2"

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases/"

libraryDependencies ++= Seq(
	"com.livestream" %% "scredis" % "2.0.6"
)
