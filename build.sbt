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
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.apache.poi" % "poi" % "3.12",
  "org.apache.poi" % "poi-ooxml" % "3.12",
  "org.apache.poi" % "poi-ooxml-schemas" % "3.12",
  "org.apache.poi" % "poi-scratchpad" % "3.12",
  "org.slf4j" % "slf4j-api" % "1.6.1",
  "org.slf4j" % "slf4j-log4j12" % "1.6.1",
  "org.slf4j" % "slf4j-simple" % "1.6.1",
  "rome" % "rome" % "1.0",
  "javax.mail" % "mail" % "1.4.5",
  "commons-io" % "commons-io" % "2.4",
  "jdom" % "jdom" % "1.0",
  "log4j" % "log4j" % "1.2.15",
  "org.apache.xmlbeans" % "xmlbeans" % "2.6.0",
  "xmlpull" % "xmlpull" % "1.1.3.4d_b4_min",
  "org.yaml" % "snakeyaml" % "1.12",
  "org.htmlparser" % "htmlparser" % "2.1",
  "org.jsoup" % "jsoup" % "1.8.3"
)

play.Project.playJavaSettings
