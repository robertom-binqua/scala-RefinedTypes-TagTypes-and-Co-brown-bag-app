package brown.bag.experiment.types

import brown.bag.experiment.types.utils.ConstraintUtil._
import brown.bag.experiment.types.utils.ValidationHelper
import brown.bag.experiment.types.utils.ValidationHelper.{toError, validOrNot, validate, validateNot}
import eu.timepit.refined.W
import eu.timepit.refined.api.{Refined, RefinedTypeOps, Validate}
import eu.timepit.refined.predicates.all._
import play.api.data.validation.Constraint

object model {

  object companyNumber {

    // How to define a custom refined type?
    // It is a 4 steps process
    // Step 1 of 4
//        type CompanyNumber1 = Refined[String, CompanyNumberPredicate] // and  using the infix notation will be
    type CompanyNumber = String Refined CompanyNumberPredicate

    // Step 2 of 4
    final case class CompanyNumberPredicate()

    // Step 3 of 4
    implicit val companyNumberValidate: Validate.Plain[String, CompanyNumberPredicate] =
      Validate.fromPredicate(
        f = ValidationHelper.validOrNot(_, playConstraints.constraint),
        showExpr = ValidationHelper.toError(_, playConstraints.constraint),
        p = CompanyNumberPredicate()
      )

    // Step 4 of 4 ... not necessary but it helps, otherwise you have to use another api.
    object CompanyNumber extends RefinedTypeOps[CompanyNumber, String] {

      // skip this for the moment ... we will see it during at CompanyNumberSpec ;-)
      def withLastNumberPlus1(aCompanyNumber: CompanyNumber): CompanyNumber = {
        val theRawCompanyNumber = aCompanyNumber.value
        val theNewPrefix: String = theRawCompanyNumber.dropRight(1)
        val lastDigitPlusOne = lastDigitOf(theRawCompanyNumber) + 1
        val theNewDigit: Int = if (lastDigitPlusOne == 10) 0 else lastDigitPlusOne
        CompanyNumber.unsafeFrom(theNewPrefix + theNewDigit.toString)
      }

      private def lastDigitOf(theRawCompanyNumber: String): Int = theRawCompanyNumber.last.asDigit
    }

    object CompanyNumberSyntax {
      implicit class CompanyNumberOps(aCompanyNumber: CompanyNumber) {
        def withLastNumberPlus1(): CompanyNumber =
          CompanyNumber.withLastNumberPlus1(aCompanyNumber)
      }
    }

    object playConstraints {

      private val alphanumericRegex = "^[A-Z0-9]*$"

      private val companyNumberEmpty: Constraint[String] = Constraint("company_number.not_entered")(companyNumber =>
        validate(
          constraint = companyNumber.isEmpty,
          errMsg = "capture-company-number-empty.error"
        )
      )
      private val companyNumberLength: Constraint[String] = Constraint("company_number.min_max_length")(companyNumber =>
        validateNot(
          constraint = companyNumber.length == 8,
          errMsg = "capture-company-number-length.error"
        )
      )
      private val companyNumberFormat: Constraint[String] = Constraint("company_number.wrong_format")(companyNumber =>
        validateNot(
          constraint = companyNumber.toUpperCase matches alphanumericRegex,
          errMsg = "capture-company-number-format.error"
        )
      )
      val constraint: Constraint[String] = companyNumberEmpty andThen
        companyNumberLength andThen
        companyNumberFormat

    }


  }

  object positive {

    type ThePositiveId = Int Refined Positive

    object ThePositiveId extends RefinedTypeOps[ThePositiveId, Int]

  }

  object theSpecialId {

    //Lets see how combine types to create more complex predicate!

    //see WitnessExampleSpec for an example .... this strange syntax is only necessary in scala 2.12
    // but not in 2.13 SIP-23 - LITERAL-BASED SINGLETON TYPES
    //    type TheSpecialIdPredicate = Interval.ClosedOpen[W.`5`.T, W.`10`.T]
    type TheSpecialIdPredicate = Interval.ClosedOpen[5, 10]

    type TheSpecialId = Int Refined TheSpecialIdPredicate

    // be careful that TheSpecialId is only an alias .... can be confused in a method signature with TheSameSpecialId
//    type TheSameSpecialId = Int Refined Interval.ClosedOpen[W.`5`.T, W.`10`.T]
    type TheSameSpecialId = Int Refined Interval.ClosedOpen[5, 10]

    object TheSpecialId extends RefinedTypeOps[TheSpecialId, Int]

  }

  object postcode {

    // again it is worth to point out the purpose of the predicate: allow the type class process to find the validator
    // go to test and find out!!!
    type Postcode = String Refined utils.PostcodePredicate

    object Postcode extends RefinedTypeOps[Postcode, String]

    object utils {
      val postCodeRegex = """^[A-Z]{1,2}[0-9][0-9A-Z]?\s?[0-9][A-Z]{2}$"""

      val postCodeNotEntered: Constraint[String] = Constraint("postcode.not-entered")(postCode =>
        validate(
          constraint = postCode.isEmpty,
          errMsg = "capture-postcode.error.not_entered"
        )
      )
      val postCodeInvalid: Constraint[String] = Constraint("postcode.invalid-format")(postCode =>
        validateNot(
          constraint = postCode matches postCodeRegex,
          errMsg = "capture-postcode.error.invalid_format"
        )
      )

      val constraint: Constraint[String] = postCodeNotEntered andThen postCodeInvalid

      implicit val postCodeValidate: Validate.Plain[String, PostcodePredicate] =
        Validate.fromPredicate(validOrNot(_, constraint), toError(_, constraint), PostcodePredicate())

      final case class PostcodePredicate()
    }
  }

  object pureRefinedPostcode {

    type PureRefinedPostcode = String Refined And[NonEmpty, MatchesRegex["^[A-Z]{1,2}[0-9][0-9A-Z]?\\s?[0-9][A-Z]{2}$"]]

    object PureRefinedPostcode extends RefinedTypeOps[PureRefinedPostcode, String]

  }

}
