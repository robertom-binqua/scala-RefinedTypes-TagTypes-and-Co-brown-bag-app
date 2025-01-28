package brown.bag.experiment.types.utils

import play.api.data.validation.{Constraint, Invalid, Valid, ValidationResult}

object ValidationHelper {

  def validOrNot[T](t: T, constraint: Constraint[T]): Boolean =
    constraint.apply(t) match {
      case Valid => true
      case Invalid(_) => false
    }

  def toError[T](t: T, constraint: Constraint[T]): String =
    constraint.apply(t) match {
      case Valid => ""
      case Invalid(errors) => errors.mkString(",")
    }

  def validate(constraint: Boolean, errMsg: String): ValidationResult = {
    if (constraint)
      Invalid(errMsg)
    else Valid
  }

  def validateNot(constraint: Boolean, errMsg: String): ValidationResult = {
    validate(!constraint, errMsg)
  }

}
