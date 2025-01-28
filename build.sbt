ThisBuild / scalaVersion := "2.13.16"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / resolvers += DefaultMavenRepository

lazy val myAppFrontEnd = (project in file("my-app-frontend"))
  .settings(
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test,
  )
  .dependsOn(myModel)

lazy val myModel = (project in file("my-model"))
  .settings(
    name := "my-model",
    scalacOptions ++= Seq("-feature"),
    libraryDependencies ++= Dependencies.test ++ Dependencies.prod ++ Seq(Dependencies.play)
  )

lazy val refinedTypesTagTypesAndCoBrownBagApp = (project in file("."))
  .settings(name := "scala-RefinedTypes-TagTypes-and-Co-brown-bag-app")
  .aggregate(
    myModel,
    myAppFrontEnd
  )

