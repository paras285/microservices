#Microservice#

##Technologies Used
- ***Java-8***
- ***Spring Boot/Spring***
- ***Spring Cloud***

- *Eureka Naming Server*
- *Ribbon Load Balancer*
- *Hystrix Circuit Breaker*

**Topics Covered**
-	Calling different Micro-services internally and extenral API calls using Rest Template
-	Use of Service discovery and Load Balancing among different instances
-	Circuit breaker with default configurations


#Microservice# -- It is all about dividing our services into smaller services dedicatedly doing their work
**Need for calling other services** --> We need to call other services to have proper functioning of services.
**RestTemplate** is used to call other services.
*Though we can create multiple instances of  RestTemplate at method level. Instead, we can rather have it defined as Spring Bean, and we can have it autowired wherever we need it.*

**Eureka Naming Server** *It uses client side service discovery*
**Ribboon Load Balancer** *It uses client side load balancing*

**Communication and Service Discovery** : We will have multiple services running at different URL, ports. It is not good approach to have it hard-coded. Since we can have multiple instances of services be dynamically configured.
Also, for Different environment we might have different URL. So, a need of Service Discovery comes into place.

#Client-side Service Discovery v/s Server-side Service Discovery#

**Client Side Discovery** : Assuming, you want to order a pizza. You go to some search engine and Search for contact of some pizza place (In this case, that search engine is acting as Service registry) and it is returning us the register service. Then another call is made to required service. In this case, client is doing all the work. Hence client-side service discovery.

**Server Side Discovery** : You are calling someone in XYZ company and want to talk to a person. You call the reception and say you want to talk to that person. That receptionist acts as Service Registeror and further redirects your call to require person. Here, client is not doing the work. But a dedicated Server(receptionist) in this case doing work. Hence, it is Server side Discovery.

*Assuming Service A needs to call Service B* : It won`t know the URL the other service it is running at, it is only aware about the service it needs to invoke.
*Our service will call the Naming server and it will respond/ or redirect further to destination URL depending upon type of discovery we are using

**Naming Server** --> It has two main roles
- 	*Service Registry*
- 	*Service Discovery*

**Service Registry** : Any server, which gets up/down needs to register/de-register itself with Naming server.
**Service Discovery** : After services has been registered, if any service needs to invoke other service, it will tell Naming service that I need this service.
It will discover which services has been registered and will call other service if it is available.

**Steps for adding Naming Server**

Create a Spring Boot application and have its dependencies
- 	On top of main class, Add @EnableEurekaServer
- 	Add few properties such as 
		*eureka.client.fetch-registry = false*  (We don`t want this to be found out by Eureka, when Eureka is searching for registered Services)
		*eureka.client.register-with-eureka = false* (We don`t want this server to be registered with Eureka, since one naming server can be client itself and can register. Since we have only 1 naming server, making this property as false)

For client of Naming server,
	Add @EnableEurekaClient

**Load Balancer** : A service might have different instances running at different servers.
Now we are calling Naming Server to invoke, if it is having differnt instance, and if loads needs to be handled, it will make use of Load Balancer.

For load balancing, through Ribbon using Eureka Naming Server,
	*Add @LoadBalanced on top of RestTemplate*
	
##What problems can our microservice get through.##
Suppose Microservice A is calling MicroService B and Micorservice C.
Microservice is failing often/or one of the microservice is consuming more resource. It will be leading to slowness in other services as well.
- 	We don`t want our other services to face issue due to one microservie. 
- 	In that case we will be using **Hystrix Circuit-Breaker**

**Main cases to define when to break circuit**
- 	*Check past n requests.*
- 	*How many of the requests out of these n requests have failed.*
- 	*How many of the requests out of these n requests have been timeout.*
- 	*When to recheck if that service is working or not.*


##Circuit-Breaker##

**When to break circuit?**
- 	*Check past n requests.*
- 	*How many of the requests out of these n requests have failed.*
- 	*How many of the requests out of these n requests have been timeout.*
- 	*When to recheck if that service is working or not.*
**What to do when circuit breaks?**
- 	*Throw an error*
-	*Default Response (Hardcoded)*
- 	*Cache the previous responses of requests, and try if that can be used*
**When to resume requests?**
- 	*Define time when it should start checking if service is working properly*

**Steps to add Circuit-Breaker**
- 	*Add maven dependency*
-	*Adding Hystrix to application*
	@EnableCircuitBreaker on top of main application
-	*Adding @HystrixCommand to methods that needs circuit breakers.*

##Confgiuring Hystrix Behaviour

**How HYSTRIX works in general?**
-	It works using **Proxy concepts** i.e. Whenever we create a Spring bean, a proxy is created on top of class.
-	So it keeps on evaluating response it recieves from service. And if it checks if it needs to break circuit, it will break the circuit.
	
**Problem with this approach?**
Assume, we are having multiple services being called in that call and just because of one service going down, we can`t have complete fallback for that partic (Lesser response is better than No response).
We can divide that into methods and then have fallback on those methods
But, Hystrix fallback mechanism won`t work in this case. Since, It is an internal call and not being handled by Spring container.

**How to overcome this?**
Since, it is an internal API call and not being handled by Spring, we will have to create another Spring component/bean where we be invoking these bean which will be calling these services.

**Hystrix Dashboard**
It gives UI and tells which how many circuit breaker we are having, along with number of circuits that have been breaked
