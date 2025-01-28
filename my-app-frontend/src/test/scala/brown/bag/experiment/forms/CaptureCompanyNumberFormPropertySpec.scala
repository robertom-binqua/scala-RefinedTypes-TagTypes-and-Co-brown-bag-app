package brown.bag.experiment.forms

import brown.bag.experiment.forms.CaptureCompanyNumberForm.companyNumberKey
import brown.bag.experiment.types.generators.gens
import brown.bag.experiment.types.model.companyNumber._
import org.scalacheck.Shrink
import org.scalatest.GivenWhenThen
import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import play.api.data.Form

class CaptureCompanyNumberFormPropertySpec extends AnyPropSpec with ScalaCheckDrivenPropertyChecks with Matchers with GivenWhenThen {

  property(testName = "CaptureCompanyNumberForm recognised a valid company number") {

    forAll(gens.allValidCompanyNumbers)(aValidCompanyNumber => {

      val toBeBindToTheForm = Map(companyNumberKey -> aValidCompanyNumber)

      Given(s"The following form mapping $toBeBindToTheForm")

      val formBind: Form[CompanyNumber] = CaptureCompanyNumberForm.form.bind(toBeBindToTheForm)

      val expectedCompanyNumber: CompanyNumber = CompanyNumber.unsafeFrom(aValidCompanyNumber)

      formBind.value should be(Some(expectedCompanyNumber))

      Then(s"the form binds successfully to a Company Number")

    })

  }

  property(testName = "CaptureCompanyNumberForm recognised when a company number has the right chars but it is too long") {

    forAll(gens.allTooLongCompanyNumbers)(aTooLongCompanyNumber => {

      val toBeBindToTheForm = Map(companyNumberKey -> aTooLongCompanyNumber)

      Given(s"The following form mapping $toBeBindToTheForm with a CompanyNumber with length ${aTooLongCompanyNumber.length}")

      val formBind: Form[CompanyNumber] = CaptureCompanyNumberForm.form.bind(toBeBindToTheForm)

      formBind.errors.size should be(1)
      formBind.errors.head.message should be("capture-company-number-length.error")

      Then(s"the form binds recognised that the company number is too long")


    })

  }

  implicit def noShrink[T]: Shrink[T] = Shrink.shrinkAny

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 100)

}
