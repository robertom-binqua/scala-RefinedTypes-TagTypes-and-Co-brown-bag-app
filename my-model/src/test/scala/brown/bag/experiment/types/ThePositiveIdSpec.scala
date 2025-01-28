package brown.bag.experiment.types

import brown.bag.experiment.types.model.positive.ThePositiveId
import brown.bag.experiment.types.utils.TypesUtil.ErrorOr
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class ThePositiveIdSpec extends AnyFlatSpec {

  "ThePositiveId" should "contain a positive value" in {

    val aPositiveAtCompileTime: ThePositiveId = ThePositiveId(10)

    ThePositiveId.from(10)
    ThePositiveId.unsafeFrom(10)

    val maybeSomePositiveNumberAtRuntime: ErrorOr[ThePositiveId] = ThePositiveId.from(10)

    maybeSomePositiveNumberAtRuntime should be(Right(aPositiveAtCompileTime))

  }

  "ThePositiveId" should "be created from a simple Int via refined.eu.timepit.refined.auto.autoRefineV" in {

    import eu.timepit.refined.auto.autoRefineV

    val aPositiveAtCompileTime: ThePositiveId = 10

    aPositiveAtCompileTime should be (ThePositiveId(10))

  }

  "ThePositiveId" should "be used as int using import eu.timepit.refined.auto.autoUnwrap" in {

    import eu.timepit.refined.auto.{autoRefineV, autoUnwrap}

    def doSomethingWith(anInt : Int):Int = anInt * anInt

    val aPositiveAtCompileTime: ThePositiveId = 2

    doSomethingWith(aPositiveAtCompileTime) should be (4)

  }

  "refineV: ThePositiveId" should "not be negative" in {

    val maybeSomePositiveNumber: ErrorOr[ThePositiveId] = ThePositiveId.from(-1)

    maybeSomePositiveNumber should be(Left("Predicate failed: (-1 > 0)."))

  }

  "unsafeFrom" should "throw an exception for -10" in {

    val theActualException: IllegalArgumentException = intercept[IllegalArgumentException] {
      ThePositiveId.unsafeFrom(-10)
    }

    theActualException.getMessage should be("Predicate failed: (-10 > 0).")

  }
}
