enablePlugins(ProtobufPlugin)

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.20"


val sparkVer="3.5.3"

lazy val root = (project in file("."))
  .settings(
    name := "HW3",

    libraryDependencies ++= Seq(

      "org.apache.spark" % "spark-core_2.12" % sparkVer,
      "org.apache.spark" % "spark-mllib_2.12" % sparkVer,
      //For logging
      "org.slf4j" % "slf4j-api" % "1.7.32",
      "ch.qos.logback" % "logback-classic" % "1.2.6",

      "org.scalatest" %% "scalatest" % "3.2.9" % Test,
      "com.typesafe" % "config" % "1.4.3",

      //Ollama Model
      "io.github.ollama4j" % "ollama4j" % "1.0.89",

      //For AWS lamba and bedrock
      "com.amazonaws" % "aws-lambda-java-core" % "1.2.1",
      "com.amazonaws" % "aws-java-sdk-bedrockruntime" % "1.12.779",
      "software.amazon.awssdk" % "bedrock" % "2.29.23",
      "com.amazonaws" % "aws-lambda-java-events" % "3.14.0",

      //For Akka http server
      "com.typesafe.akka" %% "akka-http" % "10.5.3",
      "com.typesafe.akka" %% "akka-stream" % "2.9.0-M1",
      "com.typesafe.akka" %% "akka-actor-typed" % "2.9.0-M1",
      "com.typesafe" % "config" % "1.4.3",
      "com.typesafe" %% "ssl-config-core" % "0.6.1",

      "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
      "io.grpc" % "grpc-protobuf" % "1.60.0",
      "io.grpc" % "grpc-stub" % "1.60.0",
      "com.google.protobuf" % "protobuf-java" % "3.19.1",
      "com.thesamet.scalapb" %% "compilerplugin" % "0.11.11",
      "com.thesamet.scalapb" %% "scalapb-runtime" % "0.11.17",
      "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
      "com.trueaccord.scalapb" %% "scalapb-runtime" % "0.6.7",
      "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % "0.6.7"

    ),

    assembly / assemblyMergeStrategy  := {
    case PathList("META-INF", xs@_*) =>
      xs match {
        case "MANIFEST.MF" :: Nil => MergeStrategy.discard
        case "services" :: _      => MergeStrategy.concat
        case _                    => MergeStrategy.discard
      } // Discard META-INF files
    case "reference.conf" => MergeStrategy.concat
    case x if x.endsWith(".proto") => MergeStrategy.rename
    case x if x.contains("hadoop") => MergeStrategy.first
    case _ => MergeStrategy.first
    }

  )

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)


//PB.targets in Compile := Seq(
//  (sourceManaged in Compile).value / "protobuf" -> PB.gens.scalapb
//)
