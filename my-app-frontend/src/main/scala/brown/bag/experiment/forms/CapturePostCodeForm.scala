package brown.bag.experiment.forms

import brown.bag.experiment.types.model
import brown.bag.experiment.types.model.postcode.Postcode
import play.api.data.Form
import play.api.data.Forms.{mapping, text}

import eu.timepit.refined.auto._

object CapturePostCodeForm {

  private[forms] val postCode: String = "postcode"

  val postCodeForm: Form[Postcode] = Form(
    mapping(postCode -> text.verifying(model.postcode.utils.constraint))
    ((aString: String) => Postcode.unsafeFrom(aString))
    ((aPostcode: Postcode) => Some(aPostcode))
  )

  //  val postCodeRegex = """^[A-Z]{1,2}[0-9][0-9A-Z]?\s?[0-9][A-Z]{2}$"""
  //
  //  val postCodeNotEntered: Constraint[String] = Constraint("postcode.not-entered")(
  //    postCode => validate(
  //      constraint = postCode.isEmpty,
  //      errMsg = "capture-postcode.error.not_entered"
  //    )
  //  )
  //
  //  val postCodeInvalid: Constraint[String] = Constraint("postcode.invalid-format")(
  //    postCode => validateNot(
  //      constraint = postCode matches postCodeRegex,
  //      errMsg = "capture-postcode.error.invalid_format"
  //    )
  //  )
}
