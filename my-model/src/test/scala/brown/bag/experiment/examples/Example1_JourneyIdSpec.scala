package brown.bag.experiment.examples

import eu.timepit.refined.W
import eu.timepit.refined.api.{Refined, RefinedTypeOps}
import eu.timepit.refined.predicates.all.{And, EndsWith, MaxSize, StartsWith}
import org.scalatest.EitherValues._
import org.scalatest.matchers.should.Matchers._

class Example1_JourneyIdSpec extends org.scalatest.flatspec.AnyFlatSpec {

  // Important!!!
  // those are simple predicate (which one?? in eu.timepit.refined.predicates ... StringPredicates for example)!!!
  // sometimes a predicate can contain another predicate or a special type of predicate
  // .... it depends the way the validator associated to the predicate has been defined .. see Example 2,  Example 3
    type JourneyId = String Refined (StartsWith[W.`"ABC"`.T] And EndsWith[W.`"123"`.T] And MaxSize[W.`12`.T])
//  type JourneyId = String Refined (StartsWith["ABC"] And EndsWith["123"] And MaxSize[12])

  val x : "ABC" = "ABC" // what is this?? scala 2.13 literal Singleton Type concept of literal Singleton Type https://docs.scala-lang.org/sips/42.type.html

  //  type JourneyId = String Refined And[And[StartsWith[W.`"ABC"`.T], EndsWith[W.`"123"`.T]], MaxSize[W.`12`.T]]
  //  type JourneyId = String Refined And[And[StartsWith["ABC"], EndsWith["123"]], MaxSize[12]]


  object JourneyId extends RefinedTypeOps[JourneyId, String]

  "ABC-1234-123" should "be correct" in {

    val actualJourneyId: Either[String, JourneyId] = JourneyId.from("ABC-1234-123")

    actualJourneyId should be(Right(JourneyId("ABC-1234-123")))

  }

  "ABC-12345-123" should "be incorrect because too long" in {

    val actualLeftJourneyId: Either[String, JourneyId] = JourneyId.from("ABC-12345-123")

    actualLeftJourneyId match {
      case Left(value) => value should include("Predicate taking size(ABC-12345-123) = 13 failed")
      case Right(_) => fail(message = "I should have failed :(")
    }

  }

}
