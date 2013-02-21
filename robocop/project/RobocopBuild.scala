import sbt._
import sbt.Keys._

object RobocopBuild extends Build {

  lazy val robocop = Project(
    id = "robocop",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "RoboCop",
      organization := "org.qcon.justinholmes",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2",
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.5"
    )
  )
}
