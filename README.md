

# Simple ecommerce microservices in spring boot 
<hr>

## General structure of microservices
![img.png](screen/ecommerce-system.png)

<hr>

## Technologies
![img.png](screen/00.jpg)

<hr>

### I) Product-service

<hr>

#### a) Technologies
![img.png](screen/p1.png)
#### b) UI for REST-API of product-service (Swagger)
<hr>

Url: http://localhost:8080/swagger-ui/index.html

![img.png](screen/p2.png)
<hr>

### II)  Order-service
<hr>

#### a) Technologies
![img.png](screen/o1.png)
#### b) UI for REST-API of order-service (Swagger)
<hr>

Url: http://localhost:8081/swagger-ui/index.html

![img.png](screen/o2.png)
<hr>

###  III) Payment-service
<hr>

#### a) Technologies
![img.png](screen/o1.png)

##### b) We use PayPal 
![img.png](screen/img.png)

#### c) UI for REST-API of order-service (Swagger)
<hr>

Url: http://localhost:8082/{orderID}

![img.png](screen/pa2.png)