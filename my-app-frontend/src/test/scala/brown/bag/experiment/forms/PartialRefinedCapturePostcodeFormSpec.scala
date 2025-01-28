package brown.bag.experiment.forms

import CapturePostCodeForm.postCode
import brown.bag.experiment.types.model.postcode.Postcode
import org.scalatest.OptionValues.convertOptionToValuable
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import play.api.data.Form

class PartialRefinedCapturePostcodeFormSpec extends AnyFlatSpec {

  val postCodeFormUnderTest: Form[Postcode] = CapturePostCodeForm.postCodeForm

  behavior of "CapturePostCodeForm"

  it should "recognised TA14 5GF as valid postcode" in {

    // PS: let's see what implicits instances have been found!!!
    val theExpectedPostcode: Postcode = Postcode("TA14 5GF")

    val incomingPostCodeMapping = Map(postCode -> theExpectedPostcode.value)

    val maybeSomePostCode: Option[Postcode] = postCodeFormUnderTest
      .bind(incomingPostCodeMapping)
      .value

    maybeSomePostCode.value should be(theExpectedPostcode)

  }

  it should "recognised 1111 as invalid postcode" in {

    val formBind = postCodeFormUnderTest
      .bind(Map(postCode -> "1111"))

    formBind.errors.size should be(1)

    formBind.errors.head.message should be("capture-postcode.error.invalid_format")

  }

  it should "recognised a non entered postcode as invalid" in {

    val formBind = postCodeFormUnderTest.bind(Map(postCode -> ""))

    formBind.errors.size should be(1)

    formBind.errors.head.message should be("capture-postcode.error.not_entered")

  }

  it should "have a required postcode" in {

    val formBind = postCodeFormUnderTest.bind(Map.empty[String, String])

    formBind.errors.size should be(1)

    formBind.errors.head.message should be("error.required")

  }

  it should "be filled with a valid postcode" in {

    val theExpectedPostcode = Postcode("TA14 5GF")

    val formFilled = postCodeFormUnderTest.fill(theExpectedPostcode)

    formFilled.value.value should be(theExpectedPostcode)

  }

}
