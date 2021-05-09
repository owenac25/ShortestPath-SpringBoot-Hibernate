package com.owen.assignment.service;

import java.util.List;

import com.owen.assignment.model.Planet;

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