<h1 align="center">MyRetail Api</h1>

## Table of Contents
- [Solution](#solution)
- [Technology Stack](#technology-stack)
- [Installation](#installation)
- [REST API](#rest-api)
- [Expanding Thoughts](#expanding-thoughts)

## Solution
When calling the GET endpoint with productId, aggregrate information from different sources.
- call external app (Redsky) to get the product name.
- lookup current price from the cassandra database.

When calling the PUT endpoint, we just update the price of the product.
- look up the product by the Id
- update the price of the product in the database.

## Technology Stack
- Spring boot
- Cassandra
- Gradle
- Docker

## Installation
Gradle build and run cassandra locally or connect to a cassandra server
```sh
$ ./gradlew build && java -jar build/libs/myRetail-0.0.1-SNAPSHOT.jar
```

Docker should run a local cassandra server and the myRetail App.
```sh
$ chmod +x scripts/run_docker_compose.sh 
 ./scripts/run_docker_compose.sh
```

## Rest Api
#####GET API
```
GET /product/{id}
{
    "id": 123121, // productId
    "name": "Iphone", // product name
    "current_price": {
        "value": 700.99, // current price of the product
        "currency_code": "USD"
    }
}
```

#####PUT API
```
PUT /product/{id}
Request:
{
    "current_price": {
        "value": 899.99, // current price of the product
        "currency_code": "USD"
    }
}
```

## Examples

GET Endpoint:
```bash
curl -X GET http://localhost:9003/products/13860428 

```
```json

{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": 19.98,
        "current_code": "USD"
    }
}
```
```bash
curl -X GET http://localhost:9003/products/138604
```
```json
{
    "timestamp": "2018-08-21T19:05:21.447+0000",
    "status": 404,
    "error": "Not Found",
    "message": "Resource not found!",
    "path": "/products/1386028"
}
```

```bash
curl -X PUT \
  http://localhost:9003/products/13860428 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
	"current_price": {
        "value": 294.44,
        "current_code": "USD"
    }
}'
```

## Expanding Thoughts

- Implement security over the endpoints
- Configuring SSL
- Improving Configuration (i.e specifying cassandra servers, ports etc)
- Expanding tests and load testing
- Improve Logging
- Implement caching to reduce calls made to database or external services.
