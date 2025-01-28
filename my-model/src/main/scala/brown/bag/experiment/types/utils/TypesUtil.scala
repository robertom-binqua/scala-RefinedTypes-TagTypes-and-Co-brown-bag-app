package brown.bag.experiment.types.utils

object TypesUtil {

  type ErrorOr[A] = Either[String, A]

}
