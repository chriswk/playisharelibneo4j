import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "isharelib"
  val appVersion      = "1.0-SNAPSHOT"
  val springVersion   = "3.2.2.RELEASE"
  val springDataVersion = "2.2.0.RELEASE"
  val neo4jVersion = "1.9.2"
  val appDependencies = Seq(
    jdbc,
    "com.typesafe.slick" %% "slick" % "1.0.1",
    "com.typesafe.play" %% "play-slick" % "0.3.3",
    "org.scalaj" % "scalaj-time_2.10.2" % "0.7"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    scalaVersion := "2.10.2",
    resolvers += "Spring releases" at "http://repo.springsource.org/release",
    resolvers += "Spring snapshot" at "http://repo.springsource.org/snapshot/",
    resolvers += "Maven central" at "http://repo1.maven.org/maven2",
    resolvers += "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/"
  )

}
