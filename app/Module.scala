import com.google.inject.AbstractModule
import models._
import services._

class Module extends AbstractModule {

  override def configure() = {

    bind(classOf[Dao]).toInstance(InMemoryDao)
    bind(classOf[Service]).to(classOf[ProductService])

  }

}
