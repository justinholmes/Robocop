import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "Robocop"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    javaJpa,
    "com.github.aloiscochard.sindi" % "sindi-core_2.10" % "1.0-RC3",
    "org.twitter4j" % "twitter4j-stream" % "3.0.3"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
