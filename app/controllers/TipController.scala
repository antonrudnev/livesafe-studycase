package controllers

import play.api.mvc._
import models._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class TipController extends Controller {

  def index = Action.async { implicit request =>
    DataRepository.Tips.all.map(tips => Ok(Json.toJson(tips)))
  }

  def show(id: Int) = Action.async { implicit request =>
    DataRepository.Tips.lookup(id).map(tip => if (!tip.isEmpty) {
      Ok(Json.toJson(tip))
    } else {
      NotFound(Json.obj("status" -> "Not Found"))
    })
  }

  def create = Action.async { implicit request =>
    val json = request.body.asJson.get
    DataRepository.Tips.create(json.validate[Tip].get).map(result => if (result == 1) {
      Ok(Json.toJson("status" -> "Ok"))
    } else {
      NotFound(Json.obj("status" -> "Not Found"))
    })
  }

  def update = Action.async { implicit request =>
    val json = request.body.asJson.get
    DataRepository.Tips.update(json.validate[Tip].get).map(result => if (result == 1) {
      Ok(Json.toJson("status" -> "Ok"))
    } else {
      NotFound(Json.obj("status" -> "Not Found"))
    })
  }

  def delete(id: Int) = Action.async { implicit request =>
    DataRepository.Tips.delete(id).map(result => if (result == 1) {
      Ok(Json.toJson("status" -> "Ok"))
    } else {
      NotFound(Json.obj("status" -> "Not Found"))
    })
  }
}