package brown.bag.experiment.forms

import brown.bag.experiment.types.Utr
import org.scalatest.OptionValues._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

import scala.collection.immutable

class CaptureSautrFormSpec extends AnyFlatSpec {

  behavior of "CapturePostCodeForm"

  it should "recognised 1234567890 as valid Utr" in {

    val theExpectedUtr: Utr = Utr.unsafeFrom(value = "1234567890")

    val formBind = CaptureSautrForm.form.bind(
      Map(CaptureSautrForm.saUtrKey -> theExpectedUtr.value)
    )

    formBind.value.value should be(theExpectedUtr)

  }

  it should "recognised an invalid Utr" in {

    val invalidUtrs: immutable.Seq[String] = List("1", "123456789", "12345678901", "123456789012")

    invalidUtrs.foreach(invalidUtr => {
      val formBind = CaptureSautrForm.form.bind(
        Map(CaptureSautrForm.saUtrKey -> invalidUtr)
      )
      formBind.errors.head.message should be("capture-sa-utr.error")
    }
    )
  }

  it should "be filled with a valid Utr" in {

    val theExpectedUtr: Utr = Utr.unsafeFrom(value = "1234567890")

    val maybeAFilledForm: Option[Utr] = CaptureSautrForm.form.fill(theExpectedUtr).value

    maybeAFilledForm.value should be(theExpectedUtr)

  }

}
