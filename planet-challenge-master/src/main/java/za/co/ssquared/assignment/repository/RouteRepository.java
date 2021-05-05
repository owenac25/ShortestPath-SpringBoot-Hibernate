package za.co.ssquared.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import za.co.ssquared.assignment.model.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

	/**
	 * 
	 * Search method to return all available routes from a planet 
	 * 
	 * @param planetOrigin The primary key of a planet object for which we are finding all available routes
	 * @return             A list of all available routes
	 */
	List<Route> findByPlanetOrigin(String planetOrigin);
}
