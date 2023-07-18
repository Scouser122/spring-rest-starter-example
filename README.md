# spring-rest-starter-example
Example of using Sprin Boot Custom Starter for creating RestTemplate to make requests to another service.
Instructions to use:
1. Run gradle publish job in **rest-starter** project to publish starter to local maven repository
2. Change contents of rest-client/src/main/resources/application.yaml to configure RestTemplate parameters
   - Set **pool-size** to setup connection pool manager size
   - Set **timeout** properties
5. Run rest-client with default port **8080**
6. Run rest-server with default port **9090**
7. Make GET request to client with x and y as request params, example: http://localhost:8080/sum?x=5&y=20
8. **rest-client** will use RestTemplate from **rest-starter** to make request to **rest-server** and returns result as in example: {"result":25.0}
