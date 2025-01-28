import play.core.PlayVersion
import sbt._

object AppDependencies {

  val compile = Seq(
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.12.3",
    Dependencies.playRefined
  )

  val test = Seq(
    "org.scalatest" %% "scalatest" % "3.2.9" % Test,
    "com.typesafe.play" %% "play-test" % PlayVersion.current % Test,
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test",
    Dependencies.scalaCheck % Test,
    Dependencies.refinedScalaCheck % Test,
  )
}
