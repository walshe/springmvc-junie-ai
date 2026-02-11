1. Create a Beer JPA entity. The JPA entity should use Project Lombok Getters, Setters, Builder, NoArgs and AllArgs constructors. Use Integer for the ID and version. Add the properties String beerName, String beerStyle, String upc, Integer quantityOnHand, BigDecimal price, and JPA properties for createdDate and updateDate using LocalDateTime. Also make sure all the tests pass

2. in the package controllers, create a new Spring mvc controller for the beer entity. Add operations for create, get by id and list all. in the package services create a service interface and implementation. add methods as needed to support the controller operations using the spring data repository. The controller should only use the service and the service will use the Spring Data JPA repository for persistence operations. Create spring mockmvc tests for the controller operations. Verify tests are passing

3. inspect the beer controller, add API endpoints for update and delete. Create new service methods. Add additional mock MVC tests for the new API operations. create a unit test to test all service operations

