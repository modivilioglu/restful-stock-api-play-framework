/**
  * Created by mehmetoguzdivilioglu on 08/02/2017.
  */
package object models {

  import play.api.libs.json._
  import play.api.libs.json.Reads._
  import play.api.libs.functional.syntax._
  import models._
  import scala.language.implicitConversions

  implicit def toCategory(category: String): Category = category.toLowerCase match {
    case "processor" =>  Processor
    case "graphiccard" =>  GraphicCard
    case _ =>  Other
  }
  implicit def fromCategory(category: Category): String = category match {
    case Processor => "Processor"
    case GraphicCard => "GraphicCard"
    case Other => "Other"
  }

  case class ProductStub(name: String, category: String, attributes: Map[String, String])

  implicit def fromProduct(product: Product): ProductStub = ProductStub(product.name, product.category.name, product.attributes)
  implicit def toProduct(productStub: ProductStub): Product = Product (productStub.name, productStub.category.name, productStub.attributes)

  implicit val productReads: Reads[ProductStub] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "category").read[String] and
      (JsPath \ "attributes").read[Map[String, String]]
    ) (ProductStub.apply(_, _, _))

  implicit val productWrites: Writes[ProductStub] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "category").write[String] and
      (JsPath \ "attributes").write[Map[String, String]]
    ) (unlift(ProductStub.unapply))

  implicit val categoryAndProductWrites: Writes[(String, Int)] = (
    (JsPath \ "category").write[String] and
      (JsPath \ "products").write[Int]
    ) (unlift(Tuple2.unapply[String, Int]))

  implicit val errorReads: Reads[Error] = (
    (JsPath \ "status").read[Int] and
      (JsPath \ "message").read[String] and
      (JsPath \ "description").read[String]
    ) (Error.apply(_, _, _))

  implicit val errorWrites: Writes[Error] = (
    (JsPath \ "status").write[Int] and
      (JsPath \ "message").write[String] and
      (JsPath \ "description").write[String]
    ) (unlift(Error.unapply))
}
