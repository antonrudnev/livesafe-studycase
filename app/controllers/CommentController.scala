package controllers

import models._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._

class CommentController extends Controller {

  def index(tipId: Int) = Action.async { implicit request =>
    DataRepository.Comments.all(tipId).map(comments => Ok(Json.toJson(comments)))
  }

  def show(id: Int) = Action.async { implicit request =>
    DataRepository.Comments.lookup(id).map(comment => if (!comment.isEmpty) {
      Ok(Json.toJson(comment))
    } else {
      NotFound(Json.obj("status" -> "Not Found"))
    })
  }

  def create = Action.async { implicit request =>
    val json = request.body.asJson.get
    DataRepository.Comments.create(json.validate[Comment].get).map(result => if (result == 1) {
      Ok(Json.toJson("status" -> "Ok"))
    } else {
      NotFound(Json.obj("status" -> "Not Found"))
    })
  }

  def delete(id: Int) = Action.async { implicit request =>
    DataRepository.Comments.delete(id).map(result => if (result == 1) {
      Ok(Json.toJson("status" -> "Ok"))
    } else {
      NotFound(Json.obj("status" -> "Not Found"))
    })
  }
}