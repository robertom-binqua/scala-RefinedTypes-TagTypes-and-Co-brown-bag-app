package brown.bag.experiment.json

import org.scalacheck.{Gen, Shrink}
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import play.api.libs.json._
import brown.bag.experiment.types.generators.gens
import brown.bag.experiment.types.generators.gens.{allCompanyNumbersWithOneWrongChar, allTooLongCompanyNumbers, allValidCompanyNumbers, allValidSpecialId}
import brown.bag.experiment.types.json.InputData

class InputDataRefinedJsonPropertySpec extends AnyPropSpec with ScalaCheckDrivenPropertyChecks with Matchers with GivenWhenThen {

  property(testName = "all valid incoming custom json can be converted into an InputData") {

    val validCustomInputDataJson: Gen[String] = for {
      validSpecialId <- gens.allValidSpecialId
      validCompanyNumber <- gens.allValidCompanyNumbers
    } yield toCustomJson(validSpecialId, validCompanyNumber)

    forAll((validCustomInputDataJson, "incomingJson"))(aValidJsonInputData => {

      Given(message = s"A valid incoming json:\n$aValidJsonInputData")

      //here I did not use implicit only to make it clear.
      val actualInputData = Json.parse(aValidJsonInputData).as[InputData](InputData.theInputDataReads)

      Then(message = s"we can create a valid InputData $actualInputData")

    })

  }

  property(testName = "all incoming custom json with a too long company numbers cannot be converted into an InputData") {

    val invalidCustomInputDataBecauseCompanyNumberTooLong: Gen[String] = for {
      validSpecialId <- allValidSpecialId
      tooLongCompanyNumber <- allTooLongCompanyNumbers
    } yield toCustomJson(validSpecialId, tooLongCompanyNumber)


    forAll((invalidCustomInputDataBecauseCompanyNumberTooLong, "incomingJson"))(anInvalidJsonInputData => {

      Given(message = s"An incoming json with a too long company number:\n$anInvalidJsonInputData")

      val actualInputData: JsResult[InputData] = Json.parse(anInvalidJsonInputData).validate[InputData](InputData.theInputDataReads)

      actualInputData match {
        case aJsSuccess@JsSuccess(value, path) => fail(message = s"I should have failed. $aJsSuccess")
        case JsError(errors) =>
          Then(message = s"we get the correct error:\n$errors")
          errors.toString() should include("capture-company-number-length.error")
        // sorry about that kind of .toString() assertion ;-)
      }

    })

  }

  property(testName = "all incoming custom json with a company numbers with only 1 wrong char cannot be converted into an InputData") {

    val invalidBecauseCompanyNumberHasAWrongChar: Gen[String] = for {
      validSpecialId <- allValidSpecialId
      aCompanyNumbersWithOneWrongChar <- allCompanyNumbersWithOneWrongChar
    } yield toCustomJson(validSpecialId, aCompanyNumbersWithOneWrongChar)

    forAll((invalidBecauseCompanyNumberHasAWrongChar, "incomingJson"))(anInvalidJsonInputData => {

      Given(message = s"An incoming json with a company number with a wrong char:\n$anInvalidJsonInputData")

      val actualInputData: JsResult[InputData] = Json.parse(anInvalidJsonInputData).validate[InputData](InputData.theInputDataReads)

      actualInputData match {
        case aJsSuccess@JsSuccess(value, path) => fail(message = s"I should have failed. $aJsSuccess")
        case JsError(errors) =>
          Then(message = s"we get the correct error:\n$errors")
          errors.toString() should include("capture-company-number-format.error")
        // sorry about that kind of .toString() assertion ;-)
      }

    })

  }

  property(testName = "all valid incoming default json can be converted into an InputData") {

    val validCustomInputDataJson: Gen[String] = for {
      validSpecialId <- allValidSpecialId
      validCompanyNumber <- allValidCompanyNumbers
    } yield toDefaultJson(validSpecialId, validCompanyNumber)

    forAll((validCustomInputDataJson, "incomingJson"))(aValidJsonInputData => {

      Given(message = s"A valid incoming json:\n$aValidJsonInputData")

      val actualInputData = Json.parse(aValidJsonInputData).as[InputData](InputData.theDefaultInputDataReads)

      Then(message = s"we can create a valid InputData $actualInputData")

    })

  }

  implicit def noShrink[T]: Shrink[T] = Shrink.shrinkAny

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 200)

  private def toCustomJson(validSpecialId: Int, aCompanyNumbersWithOneWrongChar: String): String = Json.prettyPrint(
    Json.obj(
      "theRefinedCompanyNumber" -> aCompanyNumbersWithOneWrongChar,
      "theSuperSpecialId" -> validSpecialId
    )
  )

  private def toDefaultJson(validSpecialId: Int, aCompanyNumbersWithOneWrongChar: String): String = Json.prettyPrint(
    Json.obj(
      "companyNumber" -> aCompanyNumbersWithOneWrongChar,
      "theSpecialId" -> validSpecialId
    )
  )

}
