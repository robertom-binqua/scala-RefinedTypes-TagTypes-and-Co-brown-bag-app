package brown.bag.experiment.types.implicits

import brown.bag.experiment.types.utils.TypesUtil.ErrorOr

object instances {

  implicit class MakeEitherThrowExceptionIfLeft[A](either: ErrorOr[A]) {
    def orThrowExceptionIfInvalid: A = throwExceptionIfInvalid(either)
  }

  private def throwExceptionIfInvalid[A](either: ErrorOr[A]): A = either match {
    case Left(error) => throw new IllegalArgumentException(error)
    case Right(value) => value
  }

}
