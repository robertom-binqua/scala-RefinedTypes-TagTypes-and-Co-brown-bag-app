package brown.bag.experiment.types

import brown.bag.experiment.types.model.theSpecialId._
import brown.bag.experiment.types.utils.TypesUtil.ErrorOr
import eu.timepit.refined.{refineMV, refineV}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class TheSpecialIdSpec extends AnyFlatSpec {

  "Refined" should "have also another API to manage instances created at compilation time" in {
    val valueCreatedViaAnotherApi: TheSpecialId = refineMV[TheSpecialIdPredicate](5)

    valueCreatedViaAnotherApi should be(TheSpecialId(5))

  }

  "Refined" should "have also another API to manage instances created at runtime" in {
    val valueCreatedViaAnotherApi: ErrorOr[TheSpecialId] = refineV[TheSpecialIdPredicate](5)

    valueCreatedViaAnotherApi should be(TheSpecialId.from(5))

  }

  "TheSpecialId" should "be created from a simple Int via eu.timepit.refined.auto._" in {

    import eu.timepit.refined.auto._

    // via implicit the compiler finds a method that takes any value T and returns a Refined
    // to be noticed that TheSpecialId definition contains all the types information we need.
    val aSpecialIdAtCompileTime: TheSpecialId = 9

    aSpecialIdAtCompileTime should be (TheSpecialId(9))

  }

  "TheSpecialId type" can "cause problems because can be confused with different alias backed by same constraint:" +
    " be careful! But we have a solution and we will show later ;-)" in {

    import eu.timepit.refined.auto._

    val sid1: TheSpecialId = 7

    val sid2: TheSameSpecialId = 8

    def m(sid1: TheSpecialId, sid2: TheSameSpecialId): Any = 1

    // ops!!!! no good :(
    m(sid2, sid1) should be(1)

    m(sid1, sid2) should be(1)

  }


  "trying to create an invalid SpecialId at compile time" should "not work" in {
    assertDoesNotCompile("import eu.timepit.refined.refineMV\n" +
      "import uk.gov.hmrc.partnershipidentificationfrontend.types.predicates.theSpecialId._\n" +
      "import eu.timepit.refined.{refineMV, refineV}\n" +
      "refineMV(10)")
  }

}
