import Settings._

val baseName = "osaifu"

ThisBuild / organization               := "com.github.kzmake"
ThisBuild / scalaVersion               := "2.13.6"
ThisBuild / semanticdbEnabled          := true
ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value)
ThisBuild / semanticdbVersion          := scalafixSemanticdb.revision // only required for Scala 2.x
ThisBuild / scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Wunused:imports" // Scala 2.x only, required by `RemoveUnused`
)

Docker / packageName := s"${baseName}/scala"
Docker / dockerRepository := Some("ghcr.io/kzmake")
Docker / maintainer := "kzmake <kamake.i3a@gmail.com>"
Docker / dockerExposedPorts := List(50051)

lazy val api = (project in file("api"))
  .settings(
    name := s"${baseName}-api"
  )
  .enablePlugins(AkkaGrpcPlugin)
  .settings(
    Compile / PB.protoSources += baseDirectory.value / "osaifu/wallet/v1"
  )

lazy val walletInfrastructure = (project in file("modules/wallet/infrastructure"))
  .settings(
    name := s"${baseName}-wallet-infrastructure"
  )
  .settings(infrastructureSettings, coreSettings)
  .dependsOn(api)
  .aggregate(api)

lazy val root = (project in file("."))
  .settings(
    name := baseName
  )
  .enablePlugins(JavaAppPackaging)
  .settings(infrastructureSettings, coreSettings)
  .dependsOn(walletInfrastructure)
  .aggregate(walletInfrastructure)
