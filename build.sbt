import Settings._

val baseName = "osaifu"

inThisBuild(
  Seq(
    organization               := "com.github.kzmake",
    scalaVersion               := "2.13.6",
    semanticdbEnabled          := true,
    scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value),
    semanticdbVersion          := scalafixSemanticdb.revision, // only required for Scala 2.x
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-Wunused:imports" // Scala 2.x only, required by `RemoveUnused`
    )
  )
)

lazy val walletInfrastructure = (project in file("modules/wallet/infrastructure"))
  .settings(
    name := s"${baseName}-wallet-infrastructure"
  )
  .enablePlugins(AkkaGrpcPlugin)
  .settings(infrastructureSettings)
  .settings(coreSettings)

lazy val root = (project in file("."))
  .settings(
    name := baseName
  )
  .settings(infrastructureSettings)
  .settings(coreSettings)
  .dependsOn(walletInfrastructure)
  .aggregate(walletInfrastructure)
