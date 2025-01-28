package brown.bag.experiment.types.generators

import eu.timepit.refined.scalacheck.numeric
import eu.timepit.refined.types.all.{NonNegInt, PosInt}
import org.scalacheck.Gen
import eu.timepit.refined.auto._

object gens {

  private val companyNumberMaxNumberOfDigits: PosInt = PosInt(8)

  val allValidCompanyNumbers: Gen[String] = for {
    aValidListOfCompanyNumberDigits <- Gen.listOfN(companyNumberMaxNumberOfDigits, Gen.alphaNumChar)
  } yield
    aValidListOfCompanyNumberDigits.mkString

  val allCompanyNumbersWithOneWrongChar: Gen[String] = {

    val allValidChars: Seq[Char] = ('0' to '9') ++ ('A' to 'Z') ++ ('a' to 'z')

    val isNotAValidChar: Char => Boolean = theWrongChar => !allValidChars.contains(theWrongChar)

    for {
      aValidCompanyNumber <- allValidCompanyNumbers
      indexWithTheWrongChar <- numeric.chooseRefinedNum(min = NonNegInt(0), max = NonNegInt.unsafeFrom(aValidCompanyNumber.length - 1))
      theWrongChar <- Gen.asciiPrintableChar.suchThat(isNotAValidChar)
      aWrongCompanyNumber <- Gen.const(aValidCompanyNumber.toVector.updated(indexWithTheWrongChar, theWrongChar))
    } yield
      aWrongCompanyNumber.mkString
  }

  val allTooLongCompanyNumbers: Gen[String] = for {
    length <- numeric.chooseRefinedNum(min = PosInt.unsafeFrom(companyNumberMaxNumberOfDigits + 1), max = PosInt(99))
    aValidListOfCompanyNumberDigits <- Gen.listOfN(length, Gen.alphaNumChar)
  } yield
    aValidListOfCompanyNumberDigits.mkString

  val allValidSpecialId: Gen[Int] = numeric
    .chooseRefinedNum(PosInt(5), PosInt(9))
    .map(_.value)

}
