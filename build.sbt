import play.Project._

name := "epub"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.2.2", 
  "org.webjars" % "bootstrap" % "2.3.1",
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

play.Project.playJavaSettings
