## Synopsis

This is a Scala Project, based on microservices to build product stock Restful Api.

## Motivation:

Individuals will be able to call the following functionalities
- Retrieve all categories along with number of products in the category, ie. processors(2), graphics cards(3)

- Retrieve a category along with the product titles i.e. processors [i5 6400, i5 4460]

- Retrieve a product with its attributes

- Add a product to a category (no attributes have to be provided, assume attributes can be added later on)

## Usage :

From the http layer client's perspective the commands can be called as below:
(Proper usage should be with curl)
- Retrieve all categories along with number of products in the category
```
curl -X GET -H "Cache-Control: no-cache" -H "Postman-Token: "http://localhost:9000/categories"
```
- Retrieve a product with its attributes
```
curl -X GET -H "Cache-Control: no-cache" -H  "http://localhost:9000/product/Geforce-1050"
```
Retrieve a category along with the product titles
```
curl -X GET -H "Cache-Control: no-cache" -H  "http://localhost:9000/product/category/GraphicCard"
```
Add a product to a category
```
curl -X POST -H "Content-Type: application/json" -d '{
"name": "ProductTest",
"category": "Processor",
"attributes": {
"memory":"16GB"
}
}' "http://localhost:9000/product"
```
## Sample response
```
{
  "name": "Geforce-1050",
  "category": "GraphicCard",
  "attributes": {
    "memory": "4GB",
    "cores": "768"
  }
}
```
## Installation:
```
sh> git clone https://github.com/modivilioglu/restful-stock-api-play-framework.git
sh> cd restful-stock-api-play-framework/
sh> sbt test
sh> sbt run
```
## Usage
To start up the microservice simply type
```
sh> sbt run
```

## Tests

There are test cases for service layer,  as well as Microservice layer. You can run them using the following command
```sh
sh> sbt test
```


## Technical Notes
The code consists of Dao, Service and Controller layers.

Controller layers are simple REST calls using the service layer apis.

Business Layer simply implements the basic business logic, mainly very simple transformation of the data it receives from the dao layer

An in memory database is generated:
It is just a simple List of Products.

Traits provide decoupling when injecting classes. Mainly Dao and Service are trains and their concrete implementations are InMemoryDao and ProductService

The model package object is the main location for implicits both for Json as well as implicit conversions from ProductStub to Product objects and vice versa.

The dependency injection is implemented by Guice, which is the standard DI for Play framework, and the Injections are made in the Module.scala class, which is the standard location in Play framework for dependency injection.

Error Handling is done in 2 ways, one in controller, and the client errors which are either not handled by controller or server errors, are directed to the ErrorHandler on Play Framework, and there the errors are formatted into the standard Json Format which is like
```
{ "status": XXX,
  "message": "some-message",
  "description": "some-description"}
```
Also for this an Error case class is defined in Models and the implicit Read / Write Json conversions are defined in models package object.

## Contributors

Mehmet Oguz Divilioglu, Email: mo.divilioglu@gmail.com

