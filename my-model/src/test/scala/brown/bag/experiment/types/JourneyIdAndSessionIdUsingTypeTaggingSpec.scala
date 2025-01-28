package brown.bag.experiment.types

import brown.bag.experiment.types.usingTypeTagging.{JourneyId, JourneyIdTag, SessionId, SessionIdTag}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import shapeless.tag

class JourneyIdAndSessionIdUsingTypeTaggingSpec extends AnyFlatSpec {

  "JourneyId and SessionId" should "be created by tagging Int type" in {

    val aJourneyId = tag[JourneyIdTag](1)

    val aSessionId = tag[SessionIdTag](2)

    def doSomething(journeyId: JourneyId, sessionId: SessionId): Int = journeyId + sessionId

    doSomething(journeyId = aJourneyId, sessionId = aSessionId) shouldBe 3

    aJourneyId shouldBe 1

    aJourneyId shouldBe 1

    aSessionId shouldBe 2

    aSessionId shouldBe (aJourneyId + 1)

    //this will not compile as prove in the test below

    //doSomething(aSessionId, aJourneyId)

  }

  "using tags" should "make code safer because if types are not ok, the code will not compile" in {
    assertTypeError("import shapeless.tag\n" +
      "import uk.gov.hmrc.partnershipidentificationfrontend.types.usingTypeTagging.{JourneyId, JourneyIdTag, SessionId, SessionIdTag}\n" +
      "\n" +
      "val aJourneyId = tag[JourneyIdTag](1)\n" +
      "\n" +
      "val aSessionId = tag[SessionIdTag](2)\n" +
      "\n" +
      "def doSomething(journeyId: JourneyId, sessionId: SessionId): Int = journeyId + sessionId\n" +
      "\n" +
      "doSomething(journeyId = aSessionId, sessionId = aPositiveAtCompileTime)\n" +
      "\n")
  }


}
