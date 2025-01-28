package brown.bag.experiment.types

import brown.bag.experiment.types.model.companyNumber.CompanyNumber
import brown.bag.experiment.types.model.companyNumber.CompanyNumberSyntax._
import brown.bag.experiment.types.utils.TypesUtil.ErrorOr
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class CompanyNumberSpec extends AnyFlatSpec {

  "A companyNumber" can "be created at runtime via the 'from' method and at compile time via the 'apply' method of the companion object" in {

    val actualCompanyNumber: ErrorOr[CompanyNumber] = CompanyNumber.from("12345678")

    //PS: We have validation at compile time: but it works only with literal!!!
    actualCompanyNumber should be(Right(CompanyNumber("12345678")))

  }

  "A companyNumber" can "be auto autoUnwrap where the base type is requested" in {

    import eu.timepit.refined.auto.autoUnwrap

    def someMethodThatTakesTheBaseType(aRawCompanyNumber: String): String = aRawCompanyNumber + aRawCompanyNumber

    someMethodThatTakesTheBaseType(aRawCompanyNumber = CompanyNumber("12345678")) should be("12345678" + "12345678")

  }

  "A companyNumber" should "be created from a simple String via refined.eu.timepit.refined.auto.autoRefineV" in {

    import eu.timepit.refined.auto.autoRefineV

    val aCompanyNumber: CompanyNumber = "12345678"

    aCompanyNumber.value should be("12345678")

  }

  "a refined class" can "be decorated with extension methods (type class Interface syntax approach): 12345678 becomes 12345679" in {

    // CompanyNumberSyntax example
    val actualCompanyNumber: Either[String, CompanyNumber] = CompanyNumber
      .from("12345678")
      .map((aCompanyNumber: CompanyNumber) => aCompanyNumber.withLastNumberPlus1())

    actualCompanyNumber should be(Right(CompanyNumber("12345679")))

  }

  "a refined class" can "be decorated with extra methods via its companion object (type class Interface object approach): 12345678 becomes 12345679" in {

    val actualCompanyNumber: Either[String, CompanyNumber] = CompanyNumber
      .from("12345678")
      .map((aCompanyNumber: CompanyNumber) => CompanyNumber.withLastNumberPlus1(aCompanyNumber))

    actualCompanyNumber should be(Right(CompanyNumber.apply("12345679")))

  }

  "a refined class" can "be decorated with extension methods (type class Interface syntax approach): 12345679 becomes 12345670" in {

    val actualCompanyNumber: Either[String, CompanyNumber] = CompanyNumber
      .from("12345679")
      .map((aCompanyNumber: CompanyNumber) => aCompanyNumber.withLastNumberPlus1())

    actualCompanyNumber should be(Right(CompanyNumber("12345670")))

  }

}
