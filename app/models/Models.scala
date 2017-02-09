package models

/**
  * Created by mehmetoguzdivilioglu on 08/02/2017.
  */
//case class Product(name: String, category: String, attributes: Map[String, String]) {
//  def addAttribute(attributeName: String, attributeValue: String): Product = {
//    Product(name, category, attributes + (attributeName -> attributeValue))
//  }
//  def this(name: String, category: Category,  attributes: Map[String, String]) = this(name, category.name, attributes)
//
//}
case class Product(name: String, category: Category, attributes: Map[String, String]) {
  def addAttribute(attributeName: String, attributeValue: String): Product = {
    Product(name, category, attributes + (attributeName -> attributeValue))
  }

}
sealed trait Category {
  val name: String
}

case object GraphicCard extends Category {
  override val name: String = "GraphicCard"
}
case object Processor extends Category{
  override val name: String = "Processor"
}
case object Other extends Category{
  override val name: String = "Other"
}
case class Error(status: Int, message: String, description: String)


