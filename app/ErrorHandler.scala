/**
  * Created by mehmetoguzdivilioglu on 08/02/2017.
  */
import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent._
import javax.inject.Singleton;
import models._
import play.api.libs.json.Json
@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Status(statusCode)(Json.toJson(Error(statusCode, message, message)))
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      InternalServerError(Json.toJson(Error(500, exception.getMessage, exception.getMessage)))
    )
  }
}