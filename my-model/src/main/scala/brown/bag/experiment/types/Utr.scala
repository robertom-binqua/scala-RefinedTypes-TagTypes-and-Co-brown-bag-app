package brown.bag.experiment.types

import brown.bag.experiment.types.implicits.instances._
import brown.bag.experiment.types.utils.TypesUtil.ErrorOr
import play.api.data.validation.{Constraint, Invalid, Valid}

import scala.language.implicitConversions

object Utr {

  // Constraint is what Play is using ....
  // so let's create a constraint to be used in the form validation from the from method
  val constraint: Constraint[String] = Constraint[String] { aString: String => {
    from(aString) match {
      case Left(error) => Invalid(error)
      case Right(_) => Valid
    }
  }}

  // this pattern is called smart constructor...
  def from(value: String): ErrorOr[Utr] =
    Either.cond(
      test = value.forall(_.isDigit) && value.length == 10,
      right = new Utr(value) {},
      left = "capture-sa-utr.error"
    )

  def unsafeFrom(value: String): Utr = from(value).orThrowExceptionIfInvalid

  def unwrap(theUtr: Utr): String = theUtr.value

  implicit def autoUnwrap(theUtr: Utr): String = unwrap(theUtr)

}

// from scala List definition we see
//sealed abstract class List[+A] extends AbstractSeq[A]

// from scala Option definition we see
//sealed abstract class Option[+A] extends Product with Serializable {
//The compiler cannot create a copy method or a constructor ....
// we decide here in this file when to create an instance of it ... see the from method in the companion object!
sealed abstract case class Utr (value: String) {

  //interesting if we see the compiled version via javap -p Utr.class

  //Compiled from "Utr.scala" shows not apply not copy methods ... because we defined as abstract case class

  //(PS: of course we can defined it simply as sealed abstract class Utr, without be a case class .... it depends if we need a case class or normal class)

  //public abstract class .....types.Utr implements scala.Product,scala.Serializable {
  //  private final java.lang.String value;
  //  public static scala.Option<java.lang.String> unapply(.....types.Utr);

  //  here we are!!! ....... copy(java.lang.String) and apply(java.lang.String) have not been created
  //  see the example below for AnAttemptOfUtr

  //  public static .....types.Utr unsafeFrom(java.lang.String);
  //  public static scala.util.Either<java.lang.String, .....types.Utr> from(java.lang.String);
  //  public static play.api.data.validation.Constraint<java.lang.String> constraint();

  //  public java.lang.String value();
  //  public .....types.Utr doThis();
  //  public .....types.Utr doThat();
  //  public java.lang.String productPrefix();
  //  public int productArity();
  //  public java.lang.Object productElement(int);
  //  public scala.collection.Iterator<java.lang.Object> productIterator();
  //  public boolean canEqual(java.lang.Object);
  //  public int hashCode();
  //  public java.lang.String toString();
  //  public boolean equals(java.lang.Object);
  //  public .....types.Utr(java.lang.String);
  //}

  // these are silly method only to show that an advantage of this approach is that we can enhance our class easily of course.
  // when we tag a type or we use refined type we need type classes to do it.

  def doThis(): Utr = ???

  def doThat(): Utr = ???
}


// functional people will be sad to see this because is not referential transparent ... it is referential "opaque"
// anyway ... as example let's put private at the constructor level so we can not do new AnAttemptOfUtr(1) everywhere
final case class AnAttemptOfUtr private(value: String) {
  require(value.forall(_.isDigit) && value.length == 10)

  // javap -p AnAttemptOfUtr.class

  //Compiled from "Utr.scala"
  //public final class .....types.AnAttemptOfUtr implements scala.Product,scala.Serializable {
  //  private final java.lang.String value;
  //  public static scala.Option<java.lang.String> unapply(.....types.AnAttemptOfUtr);

  //  public .....types.AnAttemptOfUtr copy(java.lang.String);
  //  public static .....types.AnAttemptOfUtr apply(java.lang.String);

  //  public static <A> scala.Function1<java.lang.String, A> andThen(scala.Function1<.....types.AnAttemptOfUtr, A>);
  //  public static <A> scala.Function1<A, .....types.AnAttemptOfUtr> compose(scala.Function1<A, java.lang.String>);

  //  public java.lang.String value();
  //  public java.lang.String copy$default$1();
  //  public java.lang.String productPrefix();
  //  public int productArity();
  //  public java.lang.Object productElement(int);
  //  public scala.collection.Iterator<java.lang.Object> productIterator();
  //  public boolean canEqual(java.lang.Object);
  //  public int hashCode();
  //  public java.lang.String toString();
  //  public boolean equals(java.lang.Object);
  //  public static final boolean $anonfun$new$1(char);
  //  public .....types.AnAttemptOfUtr(java.lang.String);
  //  public static final java.lang.Object $anonfun$new$1$adapted(java.lang.Object);
  //  private static java.lang.Object $deserializeLambda$(java.lang.invoke.SerializedLambda);
  //}
}
