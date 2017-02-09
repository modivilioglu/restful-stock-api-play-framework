package models

/**
  * Created by mehmetoguzdivilioglu on 08/02/2017.
  */
trait Dao {
  def getProducts: List[Product]

  def getProduct(productName: String): Option[Product]

  def addProduct(product: Product): Unit

  def removeProduct(productName: String): Unit
}

object InMemoryDao extends Dao {
  var data = List[Product](
    Product("i5-6400", Processor, Map("speed" -> "3.3Ghz", "cache" -> "6M")),
    Product("i5-4400", Processor, Map("speed" -> "3.2Ghz", "cache" -> "6M")),
    Product("Geforce-1050", GraphicCard, Map("memory" -> "4GB", "cores" -> "768")),
    Product("Radeon-R7", GraphicCard, Map("memory" -> "4GB", "memory-type" -> "DDR3")),
    Product("R7-250", GraphicCard, Map("memory" -> "1GB")))

  override def getProducts: List[Product] = {
    data
  }

  override def getProduct(productName: String): Option[Product] = {
    data.find(p => p.name == productName)
  }

  override def addProduct(product: Product): Unit = {
    data = product :: data
  }

  override def removeProduct(productName: String): Unit = {
    data = data.takeWhile(p => p.name != productName) ::: data.dropWhile(p => p.name != productName).tail
  }
}
