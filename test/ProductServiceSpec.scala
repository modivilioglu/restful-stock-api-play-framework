
import play.api.test._
import services._
import models._

import scala.language.implicitConversions
/**
  * Created by mehmetoguzdivilioglu on 08/02/2017.
  */
class ProductServiceSpec extends PlaySpecification {
  type Product = ProductStub
  "ProductService" should {
    "create product" in new WithServer(port = 9000) {
      val service = app.injector.instanceOf[Service]
      val dao = app.injector.instanceOf[Dao]
      // See if the create operation is also reflected in basic dao
      val size0 = dao.getProducts.size
      service.addProduct(Product("Prod1", GraphicCard, Map()))
      service.getProduct("Prod1") match {
        case None => failure
        case Some(product) => product.category must equalTo(GraphicCard)
      }
      val size1 = dao.getProducts.size
      (size1 - size0) must equalTo(1)
    }
    "get existing product" in new WithServer(port = 9000) {
      val service = app.injector.instanceOf[Service]
      service.getProduct("Geforce-1050") match {
        case None => failure
        case Some(product) => product.category must equalTo(GraphicCard)
      }
    }
    "get categories with right values" in new WithServer(port = 9000) {
      val service = app.injector.instanceOf[Service]
      service.getCategories.find(x => x._1 == Processor.name) match {
        case Some((_, number)) => number must equalTo(2)
        case None => failure
      }
    }
  }
}
