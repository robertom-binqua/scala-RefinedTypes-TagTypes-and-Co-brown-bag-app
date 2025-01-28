package brown.bag.experiment.types

import brown.bag.experiment.types.utils.TypesUtil.ErrorOr
import org.scalatest.matchers.should.Matchers._

class UtrSpec extends org.scalatest.flatspec.AnyFlatSpec {

  "make" should "create a correct instances" in {
    val theRawUtr = "0123456789"

    Utr.from(theRawUtr) should be(Utr.from(theRawUtr))

    Utr.from(theRawUtr) should not be theSameInstanceAs(Utr.from(theRawUtr))

    val maybeSomeUtr: ErrorOr[Utr] = Utr.from(theRawUtr)

    maybeSomeUtr match {
      case Right(theActualUtr) => theActualUtr.value should be(theRawUtr)
      case Left(_) => fail(message = "I should have failed :(")
    }

  }

  "autoUnwrap" should "allow us to pass a utr where a string is required" in {
    val theRawUtr = "0123456789"

    def someMethodThatTakesTheBaseType(theRawUtr: String): String = theRawUtr + theRawUtr

    someMethodThatTakesTheBaseType(theRawUtr = Utr.unsafeFrom(theRawUtr)) should be(theRawUtr + theRawUtr)

  }

  "it" should "not be possible invoke copy on an Utr value" in {

    assertDoesNotCompile(
      "Utr.unsafeFrom(\"0123456789\").copy(value = \"0123456789\")"
    )

  }

  "it" should "not be possible invoke apply on an Utr value" in {

    assertDoesNotCompile("Utr.apply(\"\"))")

  }
}
