package brown.bag.experiment.types.utils

import play.api.data.Forms.{optional, text}
import play.api.data.Mapping

object MappingUtil {

  val optText: Mapping[Option[String]] = optional(text)

  implicit class OTextUtil(mapping: Mapping[Option[String]]) {
    def toText: Mapping[String] =
      mapping.transform(
        x => x.getOrElse(""),
        x => Some(x)
      )

    def toBoolean: Mapping[Boolean] = mapping.transform(
      {
        case Some("true") => true
        case _ => false
      },
      x => Some(x.toString)
    )
  }

}
