name := "livesafe-testcase"

version := "1.0"

scalaVersion := "2.12.3"

lazy val livesafetestcase = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += guice

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.postgresql" % "postgresql" % "42.1.4",
  "joda-time" % "joda-time" % "2.9.9",
  "org.joda" % "joda-convert" % "1.8.2",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.3.0"
)

routesGenerator := InjectedRoutesGenerator