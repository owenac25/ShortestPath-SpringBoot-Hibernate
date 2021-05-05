package za.co.ssquared.assignment.service;

import java.util.List;

import za.co.ssquared.assignment.model.Planet;

/**
 * 
 * Interface for abstracting CRUD operations
 *
 */
public interface IPlanetService {

    List<Planet> findAll();
    Planet findByPlanetNode(String planetNode);
	void saveAll(List<Planet> readPlanets);
	void save(Planet planet);
	void deleteByPlanetNode(String planetNode);
}