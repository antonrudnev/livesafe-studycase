package models

import org.joda.time.DateTime
import play.api.libs.json._
import play.api.libs.json.Reads._

case class Tip(id: Int = -1,
               message: String,
               submittedBy: String,
               createdAt: DateTime = DateTime.now(),
               updatedAt: Option[DateTime] = None,
               _version: Int = 0)

object Tip {
  implicit val implicitTipWrites = new Writes[Tip] {
    override def writes(tip: Tip): JsValue = {
      Json.obj(
        "id" -> tip.id,
        "message" -> tip.message,
        "submittedBy" -> tip.submittedBy,
        "createdAt" -> tip.createdAt.toString,
        "updatedAt" -> tip.updatedAt.toString,
        "_version" -> tip._version
      )
    }
  }

  implicit val implicitTipReads = new Reads[Tip] {
    override def reads(json: JsValue): JsResult[Tip] = JsSuccess(new Tip(
      (json \ "id").asOpt[Int].getOrElse(-1),
      (json \ "message").as[String],
      (json \ "submittedBy").as[String],
      DateTime.parse((json \ "createdAt").asOpt[String].getOrElse(DateTime.now().toString)),
      (json \ "updatedAt").asOpt[String].map(d => try {
        new DateTime(d)
      } catch {
        case _: Throwable => DateTime.now()
      }),
      (json \ "_version").asOpt[Int].getOrElse(0)
    ))
  }
}