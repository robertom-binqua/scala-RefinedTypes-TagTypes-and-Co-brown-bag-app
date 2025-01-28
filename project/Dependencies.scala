import sbt._

object Dependencies {

  lazy val playJson = "com.typesafe.play" %% "play-json" % "2.8.1"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9"
  lazy val refinedCore = "eu.timepit" %% "refined" % "0.9.28"
  lazy val shapeless = "com.chuusai" %% "shapeless" % "2.3.7"
  lazy val play = "com.typesafe.play" %% "play" % "2.8.7"
  lazy val scalaCheck = "org.scalatestplus" %% "scalacheck-1-14" % "3.2.2.0"
  lazy val refinedScalaCheck = "eu.timepit" %% "refined-scalacheck" % "0.9.28"
  lazy val playRefined = "be.venneborg" %% "play28-refined" % "0.6.0"

  val test = Seq(
    scalaTest % Test,
    scalaCheck % Test,
    refinedScalaCheck % Test,
  )

  val prod = Seq(
    scalaCheck,
    refinedScalaCheck,
    playJson,
    refinedCore,
    shapeless,
    playRefined
  )

}
