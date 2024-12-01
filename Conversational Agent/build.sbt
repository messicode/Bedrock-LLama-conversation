ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.20"

lazy val root = (project in file("."))
  .settings(
    name := "Conversational Agent",
    libraryDependencies ++=Seq(

      //For logging
      "org.slf4j" % "slf4j-api" % "1.7.32",
      "ch.qos.logback" % "logback-classic" % "1.2.6",

      //Ollama Model
      "io.github.ollama4j" % "ollama4j" % "1.0.89",


      //Akka http
      "com.typesafe.akka" %% "akka-http" % "10.5.3",
      "com.typesafe.akka" %% "akka-stream" % "2.9.0-M1",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.9.0-M1",
      "com.typesafe" % "config" % "1.4.3",
      "com.typesafe" %% "ssl-config-core" % "0.6.1",
    )
  )
