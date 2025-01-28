package brown.bag.experiment.types

import brown.bag.experiment.types.model.companyNumber.CompanyNumber
import shapeless.tag
import shapeless.tag.@@

object usingTypeTagging {

  trait JourneyIdTag

  trait SessionIdTag

  type JourneyId = Int @@ JourneyIdTag

  type SessionId = Int @@ SessionIdTag

}

object usingTypeTaggingWithRefined {

  import shapeless.tag.@@

  trait ClosingCompanyNumberTag

  trait OpeningCompanyNumberTag

  type ClosingCompanyNumber = CompanyNumber @@ ClosingCompanyNumberTag

  type OpeningCompanyNumber = CompanyNumber @@ OpeningCompanyNumberTag

  def OpeningCompanyNumber(aCompanyNumber: CompanyNumber): CompanyNumber @@ OpeningCompanyNumberTag =
    tag[OpeningCompanyNumberTag](aCompanyNumber)

  def ClosingCompanyNumber(aCompanyNumber: CompanyNumber): CompanyNumber @@ ClosingCompanyNumberTag =
    tag[ClosingCompanyNumberTag](aCompanyNumber)

  // ignore what appears below for a moment maybe later. Basically, tagging is similar to asInstanceOf ... but not the same thing ;-) !!
  // what you can do with a library like @@ cannot be achieved simply with asInstanceOf: so use a library like shapeless for example.
  // (but it is not the only one)

  trait IdTag
  type Id = Int @@ IdTag
  // type IdWith = Int with IdTag

  implicit val maybeA: Option[Int] = Some(10)

  implicit def implicitOption[A, B](implicit maybeA: Option[A]): Option[A @@ B] =
    maybeA.asInstanceOf[Option[A @@ B]]

  //  implicit def implicitOptionWith[A, B](implicit maybeA: Option[A]): Option[A with B] = {
  //    maybeA.asInstanceOf[Option[A with B]]
  //  }

  implicitly[Option[Int @@ IdTag]] //this compiles

  //   implicitly[Option[Int with IdTag]] //diverging implicit expansion!!! implicit resolution fails here
  //   implicitly[Option[Id]] //diverging implicit expansion

  //  private val list: List[Int @@ Id] = List(1, 2, 3).@@@[Id]

}
