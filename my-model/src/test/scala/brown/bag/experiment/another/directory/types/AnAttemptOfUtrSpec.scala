package brown.bag.experiment.another.directory.types

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import brown.bag.experiment.types.AnAttemptOfUtr

class AnAttemptOfUtrSpec extends AnyFlatSpec {

  "from AnAttemptOfUtr" should "be possible create another instance via copy so put a validation in place like require" in {

//    new AnAttemptOfUtr("1234567890") // no construct accessible from here error due to private modifier on the constructor

    AnAttemptOfUtr.apply("1234567890").copy(value = "1234567891") should be(AnAttemptOfUtr("1234567891"))

//    reasoning about referential transparency and the substitution model:

//    def m(): Int = throw new IllegalArgumentException
//
//    val x = 3  // 3 it is always 3 ... independently from where you put it. The meaning of 3 is ... 3. You dont need anything else.
    
//    It is like threading programming if you dont synchronise things.... you have x shared between threads ... you think it is the same x but it is not :(
//    ....

//    this program below does not work!!! ... but if you apply the substitution model the new program return 1. :-(

//    val x: Int = m()  // ... but this x is different from the x above. Its meaning depends on other things, maybe on its history for example (not in this case but in case of mutable object yes)
//
//    try {
//      val y = x + x
//    } catch {
//      case x: IllegalArgumentException => 1
//    }
  }

  "Given an invalid utr because too short, AnAttemptOfUtr" should "throws an exception" in {

    val theActualException: IllegalArgumentException = intercept[IllegalArgumentException] {
      AnAttemptOfUtr.apply("123456789")
    }

    theActualException.getMessage should be("requirement failed")

  }

}

