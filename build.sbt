name := """veontest"""
organization := "com.lunatech"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"


libraryDependencies ++= Seq(
  ws,
  "org.jsoup" % "jsoup" % "1.10.3",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test,
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
  "com.github.tototoshi" %% "scala-csv" % "1.3.4",
  "com.github.tminglei" %% "slick-pg" % "0.15.0",
  guice
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.lunatech.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.lunatech.binders._"
