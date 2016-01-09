name := """play-jpa-multitenant"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  jdbc,
  evolutions,
  javaJpa,
  "mysql" % "mysql-connector-java" % "5.1.21",
  "org.hibernate" % "hibernate-entitymanager" % "5.0.5.Final",
  "org.hibernate" % "hibernate-envers" % "5.0.5.Final",
  "c3p0" % "c3p0" % "0.9.1.2"
)     

lazy val root = (project in file(".")).enablePlugins(PlayJava)

routesGenerator := InjectedRoutesGenerator

fork in run := false

PlayKeys.externalizeResources := false