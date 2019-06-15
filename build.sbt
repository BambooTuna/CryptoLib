import Settings._

lazy val sample = (project in file("sample"))
  .settings(commonSettings)
  .settings(
    name := "CryptoLib-sample",
    version := "1.0.0-SNAPSHOT",
    libraryDependencies ++= Seq()
  )
  .dependsOn(root)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "CryptoLib",
    version := "2.1.1-SNAPSHOT",
    libraryDependencies ++= Seq()
  )