package za.co.ssquared.assignment.service;

import za.co.ssquared.assignment.model.Planet;
import za.co.ssquared.assignment.repository.PlanetRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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