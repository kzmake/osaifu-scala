val scala2Version = "2.13.6"

val akkaVersion     = "2.6.16"
val akkaHttpVersion = "10.2.6"
val akkaGrpcVersion = "2.1.0"

inThisBuild(
  List(
    scalaVersion      := scala2Version,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision, // only required for Scala 2.x
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-Wunused:imports" // Scala 2.x only, required by `RemoveUnused`
    )
  )
)

ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value)

lazy val root = project
  .in(file("."))
  .enablePlugins(AkkaGrpcPlugin)
  .settings(
    name    := "osaifu-scala",
    version := "0.1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"          % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http2-support" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"   % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"        % akkaVersion,
      "com.typesafe.akka" %% "akka-discovery"     % akkaVersion,
      "com.typesafe.akka" %% "akka-pki"           % akkaVersion,

      // The Akka HTTP overwrites are required because Akka-gRPC depends on 10.1.x
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http2-support"       % akkaHttpVersion,
      "ch.qos.logback"     % "logback-classic"          % "1.2.6",
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"      % akkaVersion % Test,
      "org.scalatest"     %% "scalatest"                % "3.2.10"    % Test
    )
  )
