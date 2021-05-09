package com.owen.assignment.util;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owen.assignment.model.Planet;
import com.owen.assignment.model.Route;
import com.owen.assignment.service.IPlanetService;
import com.owen.assignment.service.IRouteService;

/**
 * Utility class for determining the shortest path between Earth and a destination planet
 *
 */
public class PathSolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathSolver.class);
    
    private static PathSolver pathSolverInstance = null;
	private static IPlanetService planetService;
	private static IRouteService routeService;
	
	public static void solvePaths(IPlanetService planetServ, IRouteService routeServ) throws Exception {
		if (pathSolverInstance == null) {
			pathSolverInstance = new PathSolver(planetServ, routeServ);
		}		
	}
	
	/**
	 * Use of Constructor Injection to ensure Services are available
	 * Also ensures start planet will always be Earth
	 * Constructor calls djikstra implementation 
	 */
	private PathSolver(IPlanetService planetServ, IRouteService routeServ) throws Exception {
		planetService = planetServ;
		routeService = routeServ;
		djikstraPath(planetServ.findByPlanetNode("A"));
	}

	/**
	 * Implementing Djikstrá algorithm to find the shortest path between earth and destination planet
	 * @param start       Planet to use as start planet 
	 */
    private static void djikstraPath(Planet start) throws Exception{
    	try {
		start.setDistance(0);
		PriorityQueue<Planet> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(start);
		start.setVisited(true);
       
		while( !priorityQueue.isEmpty() ){
			Planet currentPlanet = priorityQueue.poll();
			addNeighbours(currentPlanet);
			for(Route route: currentPlanet.getAvailableRoutes()){
			Planet toPlanet = planetService.findByPlanetNode(route.getPlanetDestination());
				
				if(!toPlanet.isVisited())
				{
					    double newDistance = currentPlanet.getDistance() + route.getDistance();
                        if( newDistance < toPlanet.getDistance() ){
						priorityQueue.remove(toPlanet);
						toPlanet.setDistance(newDistance);
						toPlanet.setPrevious(currentPlanet);
						priorityQueue.add(toPlanet);
					}
				}
			}
			currentPlanet.setVisited(true);
		}
    	}
    	catch (Exception ex) {
			throw new Exception(ex);
		}
	}
 
    /**
     * Method to add routes to neighbouring planets
     * @param planet  Planet we are currently looking to hop from
     */
	private static void addNeighbours(Planet planet) {
		
		List<Route> neighbours = routeService.findByPlanetOrigin(planet.getPlanetNode());
		neighbours = validateDestinations(neighbours);
		
		try{
			planet.setAvailableRoutes(neighbours);
		}
		catch(Exception ex){
			LOGGER.error("addNeighbours error ",ex);
		}
	}

	/**
	 * Method to ensure routes to planets are valid and the destination planets exist in our persistence layer
	 * @param neighbours  List of routes to neighbours of a planet
	 * @return            List of routes to neighbours of a planet less any routes to planets that do not exist in our persistence layer
	 */
	private static List<Route> validateDestinations(List<Route> neighbours) {		
		
		for (Iterator<Route> routeIterator = neighbours.iterator(); routeIterator.hasNext();) {
			Route r = routeIterator.next();
			if(Objects.isNull((planetService.findByPlanetNode(r.getPlanetDestination())))) {
				routeIterator.remove();
			}
		}
		return neighbours;	
	}
}
