package models

import play.api.libs.json._

case class Comment(id: Int = -1,
                   tipId: Int,
                   message: String)

object Comment {
  implicit val implicitCommentWrites = new Writes[Comment] {
    def writes(comment: Comment): JsValue = {
      Json.obj(
        "id" -> comment.id,
        "tipId" -> comment.tipId,
        "message" -> comment.message
      )
    }
  }

  implicit val implicitCommentReads = new Reads[Comment] {
    override def reads(json: JsValue): JsResult[Comment] = JsSuccess(new Comment(
      (json \ "id").asOpt[Int].getOrElse(-1),
      (json \ "tipId").as[Int],
      (json \ "message").as[String]
    ))
  }
}