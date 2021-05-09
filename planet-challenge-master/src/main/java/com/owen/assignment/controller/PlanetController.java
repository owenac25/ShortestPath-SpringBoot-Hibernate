package com.owen.assignment.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owen.assignment.exceptions.PlanetNotFoundException;
import com.owen.assignment.model.Planet;
import com.owen.assignment.model.Route;
import com.owen.assignment.service.IPlanetService;
import com.owen.assignment.service.IRouteService;
import com.owen.assignment.util.FileParser;
import com.owen.assignment.util.PathSolver;

/**
 * 
 * Controller class to create RESTful web services using Spring MVC
 * Maps request data to the defined request handler methods
 *
 */
@RestController
@RequestMapping("/service")
public class PlanetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetController.class);
    
    private static final String[] planetHeaders = new String[]{"planetNode", "planetName"};
    private static final String[] routeHeaders = new String[]{"routeId", "planetOrigin","planetDestination","distance"};
    
    @Autowired
    IPlanetService planetService;
    
    @Autowired
    IRouteService routeService;
    
    /**
     * Method to load data from source files into persistence layer on startup
     *  
     */
    @PostConstruct
    public void loadPlanets() throws IOException {
        
    	InputStream resource = new ClassPathResource("data/planets.txt").getInputStream();       
        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
        	planetService.saveAll(FileParser.readText(reader, Planet.class,planetHeaders));
        }
        catch(Exception ex){ 	
        	LOGGER.error("Error reading Planets file", ex);
        }

        resource = new ClassPathResource("data/routes.txt").getInputStream();        
        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
        	routeService.saveAll(FileParser.readText(reader, Route.class,routeHeaders));
        }
        catch(Exception ex){ 	
        	LOGGER.error("Error reading Routes file", ex);
        }       
    }
	 /**
	  * Prints all planet beans stored in persistance layer to client using FileParser class
	  * 
	  * @param response      Object to provide functionality when sending HTTP responses  
	  */
    @GetMapping(path = "/planets", produces = "text/csv")
    public void findPlanets(HttpServletResponse response) throws Exception {
        List<Planet> planets = planetService.findAll();
        FileParser.writePlanets(response.getWriter(), planets, planetHeaders);
    }

    /**
     * Prints data of a specific planet
     * 
     * @param planetNode                Planet identifier
     * @param response                  Object to provide functionality when sending HTTP responses
     * @throws PlanetNotFoundException  Custom exception for when planets searched for do not exist
     */
    @GetMapping(path = "/planets/{planetNode}", produces = "text/csv")
    public void findPlanet(@PathVariable String planetNode, HttpServletResponse response) throws PlanetNotFoundException {
    	try {
    		Planet planet = planetService.findByPlanetNode(planetNode);
            FileParser.writePlanet(response.getWriter(), planet,planetHeaders);
    	}
    	catch(Exception ex){
    		throw new PlanetNotFoundException(planetNode,ex);
    	}
    }
   
    @PostMapping("/planet")
    public ResponseEntity<Planet> newPlanet(@RequestBody Planet newPlanet) {
      planetService.save(newPlanet);
      return ResponseEntity.ok(newPlanet);
    }  

    @PutMapping("/planets/{planetNode}")
    public ResponseEntity<Planet> updatePlanetDetails(@PathVariable String planetNode, @RequestBody Planet planetDetails) throws Exception {
    	
    	Planet planet = planetService.findByPlanetNode(planetNode);
    	
    	if(Objects.isNull(planet)) {
			throw new Exception("Planet not found" + planetNode);
		}

    	planet.setPlanetName(planetDetails.getPlanetName());
    	planetService.save(planet);
    	
    	return ResponseEntity.ok(planet);
    	
    }

    @DeleteMapping("/planets/{planetNode}")
    public ResponseEntity<String> deletePlanet(@PathVariable String planetNode) {
    	planetService.deleteByPlanetNode(planetNode);
    	return ResponseEntity.ok(planetNode+" is deleted");
    }
   
    /**
     * Method to find the shortest path between Earth and a destination planet
     * 
     * @param toPlanet    Identifier of Destination planet to travel to
     * @return            A String showing ordered planet hops between Earth and a destination planet to achieve the shortest path
     */
    @GetMapping("/travel/{toPlanet}")
	public String getShortestPath(@PathVariable String toPlanet) throws Exception{

		if(Objects.isNull((planetService.findByPlanetNode(toPlanet)))) {
			return "This Planet does not exist";
		}
		List<String> path = new ArrayList<>();
		PathSolver.solvePaths(planetService,routeService);
		for(Planet planet=planetService.findByPlanetNode(toPlanet);planet!=null;planet=planet.getPrevious()){
			path.add(planet.getPlanetNode());
		}
		Collections.reverse(path);
		return "Shortest path is: " + path.toString();
	}
    
    //Simple tests for polling the webservice
	@GetMapping(path = "/test")
	public String sayHello() {
		return System.currentTimeMillis() + "";
	}	

	@GetMapping(path = "/testSave")
	public void loadDb(IPlanetService planetService) {
		try {
			planetService.save(new Planet("P2", "Purlar"));
			LOGGER.info("Save Successful");	
		}
		catch(Exception ex) {
			LOGGER.error("Planet P2 could not be saved");
		}
	}
}