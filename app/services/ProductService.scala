package services

import javax.inject.Inject
import models._
/**
  * Created by mehmetoguzdivilioglu on 08/02/2017.
  */

trait Service {
  def getCategories: List[(String, Int)]
  def getProductNamesPerCategory(category: String): List[String]
  def getProduct(name: String): Option[Product]
  def addProduct(product: Product): Unit
}

class ProductService @Inject() (dao: Dao) extends Service {
  def getCategories: List[(String, Int)] = {
    val grouped = dao.getProducts.groupBy(p => p.category)
    val result = grouped.map(x => (x._1.name, x._2.size))
    result.toList
  }
  override def getProductNamesPerCategory(category: String): List[String] = {
    dao.getProducts.filter(p => p.category.name.toLowerCase() == category.toLowerCase()).map(p => p.name)
  }
  override def getProduct(name: String): Option[Product] = {
    dao.getProduct(name)
  }
  override def addProduct(product: Product): Unit = {
    dao.addProduct(product)
  }
}
object ProductService