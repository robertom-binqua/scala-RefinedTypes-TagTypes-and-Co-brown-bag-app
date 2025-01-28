package brown.bag.experiment.types.json

import be.venneborg.refined.play.RefinedJsonFormats
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Json, Reads}
import brown.bag.experiment.types.model.companyNumber.CompanyNumber
import brown.bag.experiment.types.model.theSpecialId.TheSpecialId

// a simple example of case class that uses Refined type
final case class InputData(companyNumber: CompanyNumber, theSpecialId: TheSpecialId)

object InputData {

  val theRefinedCompanyNumberReads: Reads[CompanyNumber] = {

    //from a Reads of the "base" type, in this case a String .....
    implicit val rawCompanyNumberReads: Reads[String] = Reads.at(JsPath \ "theRefinedCompanyNumber")(Reads.StringReads)

    // we can build a reads of the refined type via readRefined method.
    // let's see how readRefined uses the implicit above.
    // Of course specify the type on the left of = otherwise the compiler cannot guest you intention
    val theRefinedReads: Reads[CompanyNumber] = RefinedJsonFormats.readRefined
    theRefinedReads
  }

  val theSuperSpecialIdReads: Reads[TheSpecialId] = {
    implicit val rawTheSuperSpecialIdReads: Reads[Int] = Reads.at(JsPath \ "theSuperSpecialId")(Reads.IntReads)
    RefinedJsonFormats.readRefined
  }

  implicit val theInputDataReads: Reads[InputData] = (theRefinedCompanyNumberReads and theSuperSpecialIdReads) ((cn, sid) => InputData(cn, sid))


  implicit val theDefaultInputDataReads: Reads[InputData] = {
    //If we need the default reader and writer we need just to import the right implicit
    //this is important because bring into scope the implicit we need for the format macro
    import RefinedJsonFormats._
    Json.format[InputData]
  }

}
