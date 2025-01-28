package brown.bag.experiment.forms

import brown.bag.experiment.types.Utr
import play.api.data.Form
import play.api.data.Forms.{mapping, text}

object CaptureSautrForm {

  //  val SautrErrorKey: String = "capture-sa-utr.error"

  //  val form: Form[String] =
  //    Form(
  //      SautrKey -> text.verifying(SautrErrorKey, sautr => sautr.forall(_.isDigit) && sautr.length == 10)
  //    )

  private[forms] val saUtrKey: String = "sa-utr"

  val form: Form[Utr] = Form(
    mapping(saUtrKey -> text.verifying(Utr.constraint))
    ((theIncomingString: String) => Utr.unsafeFrom(theIncomingString))
    ((anUtr: Utr) => Some(anUtr))
  )

}
