package brown.bag.experiment.examples

import eu.timepit.refined.W
import eu.timepit.refined.api.{Refined, RefinedTypeOps}
import eu.timepit.refined.predicates.all.{AllOf, EndsWith, MaxSize, StartsWith}
import org.scalatest.EitherValues._
import org.scalatest.matchers.should.Matchers._
import shapeless.{::, HNil}

class Example3_JourneyIdWithAllOfSpec extends org.scalatest.flatspec.AnyFlatSpec {

//  type JourneyIdWithAllOf = String Refined AllOf[StartsWith[W.`"ABC"`.T] :: EndsWith[W.`"123"`.T] :: MaxSize[W.`12`.T] :: HNil]
  type JourneyIdWithAllOf = String Refined AllOf[StartsWith["ABC"] :: EndsWith["123"] :: MaxSize[12] :: HNil]

  //type JourneyIdWithAllOf = String Refined AllOf[::[StartsWith[W.`"ABC"`.T], ::[EndsWith[W.`"123"`.T], ::[MaxSize[W.`12`.T], HNil]]]]

  //val x : Int Tuple2 Int Tuple2 Int  = ((1,1), 1)  // HList is "similar" to a Tuple2

  object JourneyIdWithAllOf extends RefinedTypeOps[JourneyIdWithAllOf, String]

  "ABC-1234-123" should "be correct" in {

    val actualJourneyId: Either[String, JourneyIdWithAllOf] = JourneyIdWithAllOf.from("ABC-1234-123")

    actualJourneyId should be(Right(JourneyIdWithAllOf("ABC-1234-123")))

  }

  "ABC-12345-123" should "be incorrect" in {

    val actualJourneyId: Either[String, JourneyIdWithAllOf] = JourneyIdWithAllOf.from("ABC-12345-123")

    actualJourneyId match {
      case Left(value) => value should include("Predicate failed")
      case Right(_) => fail(message = "I should have failed :(")
    }

  }


}
