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

Docker / packageName        := s"${baseName}/scala/app"
Docker / dockerRepository   := Some("ghcr.io/kzmake")
Docker / maintainer         := "kzmake <kamake.i3a@gmail.com>"
Docker / dockerExposedPorts := List(50051)

lazy val api = (project in file("api"))
  .settings(
    name := s"${baseName}-api"
  )
  .enablePlugins(AkkaGrpcPlugin)
  .settings(protoSettings)
  .settings(
    Compile / PB.protoSources += file("../api/osaifu")
  )

lazy val walletDomain = (project in file("modules/wallet/domain"))
  .settings(
    name := s"${baseName}-wallet-domain"
  )
  .settings(infrastructureSettings, coreSettings)

lazy val walletUseCase = (project in file("modules/wallet/usecase"))
  .settings(
    name := s"${baseName}-wallet-usecase"
  )
  .settings(infrastructureSettings, coreSettings)
  .dependsOn(walletDomain)
  .aggregate(walletDomain)

lazy val walletQuery = (project in file("modules/wallet/query"))
  .settings(
    name := s"${baseName}-wallet-query"
  )
  .settings(infrastructureSettings, coreSettings)

lazy val walletInterface = (project in file("modules/wallet/interface"))
  .settings(
    name := s"${baseName}-wallet-interface"
  )
  .settings(infrastructureSettings, coreSettings)
  .dependsOn(api, walletUseCase, walletQuery)
  .aggregate(api, walletUseCase, walletQuery)

lazy val walletInfrastructure = (project in file("modules/wallet/infrastructure"))
  .settings(
    name := s"${baseName}-wallet-infrastructure"
  )
  .settings(infrastructureSettings, coreSettings)
  .dependsOn(api, walletInterface)
  .aggregate(api, walletInterface)

lazy val root = (project in file("."))
  .settings(
    name := baseName
  )
  .enablePlugins(JavaAppPackaging)
  .settings(infrastructureSettings, coreSettings)
  .dependsOn(walletInfrastructure)
  .aggregate(walletInfrastructure)
