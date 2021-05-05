Design Considerations:

1. I have chosen the Architecture (Controller -> Service -> Repository)
Use of a Service layer for further implementation of business logic to come in the future instead of repository directly being used in controller, despite current simple crud operations

2. Chose to remove all setters of Route class except for distance as distance of a route between planets may change but planets involved in that route should not

3. Assumed that destination planet to find the shortest path to would be provided as Planet Identifier/Node and not Planet Name

4. The 4th instruction from Read.md "Expose the RouteRequest document and return a RouteResponse document with the hops" was assumed to mean simply to expose and call the shortest path method

5. The 5th instruction "Create a simple example of how you called your API and returns the results" was done by using postman and storing screenshots in the folder included in the project named 'api calls'