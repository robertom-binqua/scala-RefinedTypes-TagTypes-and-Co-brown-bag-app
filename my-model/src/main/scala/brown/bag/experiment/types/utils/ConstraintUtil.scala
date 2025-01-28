package brown.bag.experiment.types.utils

import play.api.data.validation.{Constraint, Valid, ValidationResult}

object ConstraintUtil {

  def constraint[A](f: A => ValidationResult): Constraint[A] = Constraint[A]("")(f)

  implicit class ConstraintUtil[A](cons: Constraint[A]) {

    def andThen(newCons: Constraint[A]): Constraint[A] =
      constraint((data: A) =>
        cons.apply(data) match {
          case Valid => newCons.apply(data)
          case r => r
        }
      )

    def or(newCons: Constraint[A]): Constraint[A] =
      constraint((data: A) =>
        cons.apply(data) match {
          case Valid => Valid
          case invalid => newCons.apply(data) match {
            case Valid => Valid
            case _ => invalid // return the first invalid message
          }
        }
      )

  }

}
