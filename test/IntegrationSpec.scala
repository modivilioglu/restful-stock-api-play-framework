
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.test._
import models._
import scala.concurrent.Await
import scala.concurrent.duration._

class IntegrationSpec extends PlaySpecification {
  val INVALID_INPUT = "Invalid input"

  "Client" should {
    "get all repositories" in new WithServer(port = 9000) {
      val ws = app.injector.instanceOf[WSClient]
      WsTestClient.withClient { client =>
        val result = Await.result(ws.url("http://localhost:9000" + "/product/category/Processor").get(), 10.seconds)
        val resultList = Json.fromJson[List[String]](result.json).get
        resultList must contain("i5-6400")
      }
    }
    "get product" in new WithServer(port = 9000) {
      val ws = app.injector.instanceOf[WSClient]
      WsTestClient.withClient { client =>
        val result = Await.result(ws.url("http://localhost:9000" + "/product/Geforce-1050").get(), 10.seconds)
        val resultProduct = Json.fromJson[ProductStub](result.json).get
        resultProduct.category must equalTo("GraphicCard")
      }
    }
    "create new product" in new WithServer(port = 9000) {
      val ws = app.injector.instanceOf[WSClient]
      WsTestClient.withClient { client =>
        val productToAdd = Product("Asus", GraphicCard, Map[String, String]())

        val result = Await.result(ws.url("http://localhost:9000" + "/product").withHeaders(("Content-Type", "application/json")).post(Json.toJson[ProductStub](productToAdd).toString()), 10.seconds)
        val addedProduct:Product = Json.fromJson[ProductStub](result.json).get
        result.status must equalTo(200)
        addedProduct must equalTo (productToAdd)
      }
    }
    "get right error when content type is invalid" in new WithServer(port = 9000) {
      val ws = app.injector.instanceOf[WSClient]
      WsTestClient.withClient { client =>
        val productToAdd = Product("Asus", GraphicCard, Map[String, String]())

        val result = Await.result(ws.url("http://localhost:9000" + "/product").post(Json.toJson[ProductStub](productToAdd).toString()), 10.seconds)
        val resultError = Json.fromJson[Error](result.json).get
        resultError.message must equalTo (INVALID_INPUT)
      }
    }
  }
  "ErrorHandler" should {
    "expose the correct error message format in case of client error" in new WithServer(port = 9000) {
      val ws = app.injector.instanceOf[WSClient]
      WsTestClient.withClient { client =>
        val result = Await.result(ws.url("http://localhost:9000" + "/some-wrong-path").get(), 10.seconds)
        val resultError = Json.fromJson[Error](result.json).get
        resultError.status must equalTo (404)
      }
    }
  }

}
