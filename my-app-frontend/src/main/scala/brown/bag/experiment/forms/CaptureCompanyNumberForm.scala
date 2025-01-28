package brown.bag.experiment.forms

import brown.bag.experiment.types.model.companyNumber
import brown.bag.experiment.types.model.companyNumber.CompanyNumber
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import eu.timepit.refined.auto._

object CaptureCompanyNumberForm {

  private[forms] val companyNumberKey: String = "companyNumber"

  val form: Form[CompanyNumber] = Form(
    mapping(companyNumberKey -> text.verifying(companyNumber.playConstraints.constraint))
    ((anIncomingString: String) => CompanyNumber.unsafeFrom(anIncomingString))
    ((aCompanyNumber: CompanyNumber) => Some(aCompanyNumber))
  )

  //  val alphanumericRegex = "^[A-Z0-9]*$"
  //
  //  def companyNumberEmpty: Constraint[String] = Constraint("company_number.not_entered")(
  //    companyNumber => validate(
  //      constraint = companyNumber.isEmpty,
  //      errMsg = "capture-company-number-empty.error"
  //    )
  //  )
  //
  //  def companyNumberLength: Constraint[String] = Constraint("company_number.min_max_length")(
  //    companyNumber => validateNot(
  //      constraint = companyNumber.length <= 8,
  //      errMsg = "capture-company-number-length.error"
  //    )
  //  )
  //
  //  def companyNumberFormat: Constraint[String] = Constraint("company_number.wrong_format")(
  //    companyNumber => validateNot(
  //      constraint = companyNumber.toUpperCase matches alphanumericRegex,
  //      errMsg = "capture-company-number-format.error"
  //    )
  //  )

}
