package com.owen.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owen.assignment.model.Planet;
import com.owen.assignment.repository.PlanetRepository;

/**
 * 
 * Service class to contain business logic 
 * Currently simple CRUD implementations but may change according to future requirements
 *
 */
@Service
public class PlanetService implements IPlanetService {

    @Autowired
    private PlanetRepository repository;

    @Override
    public List<Planet> findAll() {
    	return repository.findAll();
    }

    @Override
    public Planet findByPlanetNode(String planetNode) {
        return repository.findById(planetNode).orElse(null);
    }
    
    @Override
    public void save(Planet planet) {
        repository.save(planet);
    }  
    
    @Override    
    public void saveAll(List<Planet> planets) {
        repository.saveAll(planets);
    }    
 
    @Override
    public void deleteByPlanetNode(String planetNode) {
    	repository.deleteById(planetNode);
    }   
    
}