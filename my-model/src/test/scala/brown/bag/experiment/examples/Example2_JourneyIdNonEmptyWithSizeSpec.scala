package brown.bag.experiment.examples

import eu.timepit.refined.W
import eu.timepit.refined.api.{Refined, RefinedTypeOps}
import eu.timepit.refined.predicates.all.{Equal, Size}
import org.scalatest.matchers.should.Matchers._

class Example2_JourneyIdNonEmptyWithSizeSpec extends org.scalatest.flatspec.AnyFlatSpec {

  type JourneyIdNonEmptyWithSize = String Refined Size[Equal[3]]

  object JourneyIdNonEmptyWithSize extends RefinedTypeOps[JourneyIdNonEmptyWithSize, String]

  "ABC-1234-123" should "be correct" in {

    val actualJourneyIdNonEmptyWithSize: Either[String, JourneyIdNonEmptyWithSize] = JourneyIdNonEmptyWithSize.from("ABC")

    actualJourneyIdNonEmptyWithSize should be(Right(JourneyIdNonEmptyWithSize("ABC")))

  }
}
