package brown.bag.experiment.examples

import eu.timepit.refined.W
import eu.timepit.refined.api.{Refined, RefinedTypeOps}
import eu.timepit.refined.predicates.all.{AnyOf, EndsWith, MaxSize, StartsWith}
import org.scalatest.matchers.should.Matchers.{be, _}
import shapeless.{::, HNil}

class Example4_JourneyIdWithAnyOfSpec extends org.scalatest.flatspec.AnyFlatSpec {

//  type JourneyIdWithAnyOf = String Refined AnyOf[StartsWith[W.`"ABC"`.T] :: EndsWith[W.`"123"`.T] :: MaxSize[W.`12`.T] :: HNil]
  type JourneyIdWithAnyOf = String Refined AnyOf[StartsWith["ABC"] :: EndsWith["123"] :: MaxSize[12] :: HNil]

  object JourneyIdWithAnyOf extends RefinedTypeOps[JourneyIdWithAnyOf, String]

  "ABC-can-be-anything" should "be correct because starts with ABC" in {

    val actualJourneyId: Either[String, JourneyIdWithAnyOf] = JourneyIdWithAnyOf.from("ABC-can-be-anything")

    actualJourneyId should be(Right(JourneyIdWithAnyOf("ABC-can-be-anything")))

  }

  "can-be-anything-123" should "be correct because ends with 123" in {

    val actualJourneyId: Either[String, JourneyIdWithAnyOf] = JourneyIdWithAnyOf.from("can-be-anything-123")

    actualJourneyId should be(Right(JourneyIdWithAnyOf("can-be-anything-123")))

  }

  "012345678912" should "be correct because has size <= 12" in {

    val actualJourneyId: Either[String, JourneyIdWithAnyOf] = JourneyIdWithAnyOf.from("012345678912")

    actualJourneyId should be(Right(JourneyIdWithAnyOf("012345678912")))

  }

  "01234567891-this is to much" should "be incorrect because does not have any of ABC, 123 and size <= 12" in {

    val actualJourneyShouldBeLeft: Either[String, JourneyIdWithAnyOf] = JourneyIdWithAnyOf.from("01234567891-this is to much")

    actualJourneyShouldBeLeft match {
      case Left(value) => value should include("Predicate failed")
      case Right(_) => fail(message = "I should have failed :(")
    }

  }

}
