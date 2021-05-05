package za.co.ssquared.assignment.service;

import java.util.List;

import za.co.ssquared.assignment.model.Route;

/**
 * 
 * Interface for abstracting CRUD operations
 *
 */
public interface IRouteService {

    List<Route> findAll();
    Route findByRoute(Integer route);
	void saveAll(List<Route> readRoutes);
	void save(Route route);
	List<Route> findByPlanetOrigin(String planetOrigin);
	void deleteById(Integer route);
}
