package brown.bag.experiment.forms

import brown.bag.experiment.forms.CaptureCompanyNumberForm.companyNumberKey
import brown.bag.experiment.types.model.companyNumber._
import org.scalatest.OptionValues.convertOptionToValuable
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class CaptureCompanyNumberFormSpec extends AnyFlatSpec {

  behavior of "CaptureCompanyNumberForm"

  it should "recognised a valid company number" in {

    val allTheExpectedCompanyNumbers: Seq[CompanyNumber] = List("12345678", "01234567", "SC000012", "1234567R", "IP1234RS")
      .map(CompanyNumber.unsafeFrom)

    allTheExpectedCompanyNumbers.foreach(expectedCompanyNumber => {

      val formBind = CaptureCompanyNumberForm.form.bind(
        Map(companyNumberKey -> expectedCompanyNumber.value)
      )

      formBind.value.value should be(expectedCompanyNumber)
    })

  }

  it should "recognised 111111111111111 as invalid company number because is longer than 8 chars" in {

    val formBind = CaptureCompanyNumberForm.form.bind(
      Map(
        companyNumberKey -> "111111111111111",
      )
    )

    formBind.errors.size should be(1)

    formBind.errors.head.message should be("capture-company-number-length.error")

  }

  it should "recognised a non entered company number as invalid" in {

    val formBind = CaptureCompanyNumberForm.form.bind(Map(companyNumberKey -> ""))

    formBind.errors.size should be(1)

    formBind.errors.head.message should be("capture-company-number-empty.error")

  }

  it should "have a required company number" in {

    val formBind = CaptureCompanyNumberForm.form.bind(Map.empty[String, String])

    formBind.errors.size should be(1)

    formBind.errors.head.message should be("error.required")

  }

  it should "be filled with a valid company number" in {

    val theExpectedCompanyNumber = CompanyNumber("12345678")

    val formFilled = CaptureCompanyNumberForm.form.fill(theExpectedCompanyNumber)

    formFilled.value.value should be(theExpectedCompanyNumber)

  }

}
