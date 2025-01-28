package brown.bag.experiment.types

import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Greater
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import shapeless.Witness

import scala.math.Numeric.IntIsIntegral

class WitnessExampleSpec extends AnyFlatSpec {

  "Witness and import eu.timepit.refined.auto._" can "be used to create a type" in {

    val witnessOf5SingletonType = Witness(5)

    //    val anInstance: Any = W.`5` // PS: this is just Any ... it is not useful in its own.
    // It has to be use with T in a context of a type ... like below
    type AnotherWitnessOf5SingletonType = W.`5`.T

    val `5` = 5

    val `"fiveAsReference"` = "five"

    `5` + `"fiveAsReference"` should be("5five")

    //SIP-23 - LITERAL-BASED SINGLETON TYPES
    //in scala 2.13 singleton types have been introduced and you can write a better syntax
    //val x : Int Refined Greater[5] = 5

    import eu.timepit.refined.auto._

    // if we show the implicits used in Intellij we can see what happen and what the macro generate behind the scene
    // on the left of = there are types ... on the right there are instances ...
    val greaterThan5: Int Refined Greater[witnessOf5SingletonType.T] = 6

    val greaterThan5Again: Int Refined Greater[W.`5`.T] = 6

    val anotherGreaterThan5: Int Refined Greater[AnotherWitnessOf5SingletonType] = 6

    // the code below does not compile because autoRefineV is a macro ... but just to have an idea,
    // these are the instances involved

//        val z: Int Refined Greater[AnotherWitnessOf5SingletonType] = autoRefineV(6)(
//          rt = RefType.refinedRefType,
//          v = Greater.greaterValidate(
//            WitnessAs.singletonWitnessAs(shapeless.Witness.apply[shapeless.Witness.Aux[W.`5`.T]]),
//            IntIsIntegral
//          )
//        )

    // the code below it does not compile and if we inspect the macro code behind autoRefineV, we will find why!!!
    // the macro use the validator behind the scene and it will stop the compilation if validation fails.
    //    val y: Int Refined Greater[witnessOf5SingletonType.T] = 3

  }


  "shapeless.Witness.Aux[W.`5`.T] apply method" should "build an instance on the fly" in {

    //see how the apply method is invoked by the implicit mechanism

    val x = shapeless.Witness.apply[W.`5`.T]

    implicitly[shapeless.Witness.Aux[W.`5`.T]].value should be(5)

    implicitly[shapeless.Witness.Aux[W.`5`.T]] should not(be(theSameInstanceAs(implicitly[shapeless.Witness.Aux[W.`5`.T]])))

  }

  "trying to create an invalid refined type using Witness at compile time" should "not work" in {
    assertTypeError("" +
      "import shapeless.Witness\n" +
      "import eu.timepit.refined.numeric.Greater\n" +
      "import eu.timepit.refined.api.Refined\n" +
      "import eu.timepit.refined.auto._\n" +
      "\n" +
      "val witness5 = Witness(5)\n" +
      "val a: Int Refined Greater[witness5.T] = 4"
    )
  }

}
