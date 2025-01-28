package brown.bag.experiment.forms

import be.venneborg.refined.play.RefinedForms.refinedStringFormat
import brown.bag.experiment.types.model.pureRefinedPostcode.PureRefinedPostcode
import play.api.data.{Form, Forms}

object CapturePureRefinedPostCodeForm {

  private[forms] val postCode: String = "postcode"

  val form: Form[PureRefinedPostcode] = Form[PureRefinedPostcode](
    postCode -> Forms.of[PureRefinedPostcode]
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
