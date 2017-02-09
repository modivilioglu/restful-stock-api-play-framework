package controllers

import javax.inject._
import models._
import play.api.libs.json._
import play.api.mvc._
import services.Service

@Singleton
class ProductController @Inject()(service: Service) extends Controller {
  val NO_DATA_FOUND = "No data found"
  val INVALID_INPUT = "Invalid input"

  def getCategories = Action {
    Ok(Json.toJson(service.getCategories))
  }

  def getProductNamesPerCategory(category: String) = Action {
    Ok(Json.toJson(service.getProductNamesPerCategory(category)))
  }

  def getProduct(name: String) = Action {
    service.getProduct(name) match {
      case None => NotFound(Json.toJson(Error(404, NO_DATA_FOUND, NO_DATA_FOUND)))
      case Some(product) => Ok(Json.toJson[ProductStub](product))
    }
  }

  def addProduct = Action { request =>
    val data = request.body.asJson
    data match {
      case None => BadRequest(Json.toJson(Error(400, INVALID_INPUT, INVALID_INPUT)))
      case Some(jsonProduct) => {
        val product = Json.fromJson[ProductStub](jsonProduct)
        service.addProduct(product.get)
        Ok(jsonProduct)
      };
    }
  }
}
