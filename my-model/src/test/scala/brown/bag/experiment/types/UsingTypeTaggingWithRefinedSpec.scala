package brown.bag.experiment.types

import brown.bag.experiment.types.model.companyNumber.CompanyNumber
import brown.bag.experiment.types.usingTypeTaggingWithRefined.{ClosingCompanyNumber, OpeningCompanyNumber}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class UsingTypeTaggingWithRefinedSpec extends AnyFlatSpec {

  "tagging an instance" should "be easy as asInstanceOf" in {

    trait ASpecialIdTag

    val anInt: Int = 1

    type ASpecialId = Int with ASpecialIdTag

    val IAmAlwaysAnIntInTheRuntimeWorld: Int with ASpecialIdTag = anInt.asInstanceOf[ASpecialId]

    (IAmAlwaysAnIntInTheRuntimeWorld + 1) should be(2)

    def doSomething(aSpecialId: ASpecialId, anInt: Int): Int = {
      // in the Runtime world aSpecialId is always an Int
      aSpecialId + anInt
    }

    //    val journeyId: String = "2"
    //    val transactionId: String = "3"
    //    val sessionId: String = "1"
    //
    //even intellij helps you ...
    doSomething(IAmAlwaysAnIntInTheRuntimeWorld, anInt)

    def doSomethingElse(theSessionId: String, theJourneyId: String, theTransactionId: String): String = {
      // do something with sessionId, journeyId, transactionId
      ???
    }

    val aTransactionId = "2"
    val aJourneyId = "1"
    val theSessionId = "3"
    //    ... and intellij cannot help us anymore:( ... try it by yourself <shift> <ctr> <space>
    //     doSomethingElse()

    //and this does not compile because ASpecialId in the compile time world is not an Int is .... Int with ASpecialIdTag
    //doSomething(anInt, IAmAlwaysAnIntInTheRuntimeWorld)
  }

  "ClosingCompanyNumber and OpeningCompanyNumber" should "be a Tagged CompanyNumber" in {

    def concat(aClosingCompanyNumber: ClosingCompanyNumber, anOpeningCompanyNumber: OpeningCompanyNumber): String = {
      //these are refined types now (at runtime!!) because we tagged 2 refined types so .... we need to use .value
      aClosingCompanyNumber.value + anOpeningCompanyNumber.value
    }

    val theClosingCompanyNumber: ClosingCompanyNumber = ClosingCompanyNumber(CompanyNumber("11111113"))

    val theOpeningCompanyNumber: OpeningCompanyNumber = OpeningCompanyNumber(CompanyNumber("11111112"))

    //even intellij helps you ... try it by yourself <shift> <ctr> <space>
    concat(theClosingCompanyNumber, theOpeningCompanyNumber) should be("1111111311111112")

    //    does not compile
    //    concat(theOpeningCompanyNumber, theClosingCompanyNumber)
  }

}
