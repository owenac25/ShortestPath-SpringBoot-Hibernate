package com.owen.assignment.model;

import java.util.Objects;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * Planet Entity created to model nodes of a Graph
 * Persists data we require to be stored in the db
 * Contains transient data to assist in shortest path algorithm
 * 
 */
@Entity
@Table(name = "planet")
public class Planet implements Comparable<Planet>{

    @Id
    @Column(name = "planet_node")
    private String planetNode;

    @Column(name = "planet_name")
	private String planetName;
    
	@Transient
    private boolean visited;
	@Transient
	private List<Route> availableRoutes;
	@Transient
	private double distance = Double.MAX_VALUE;
	@Transient
	private Planet previous;
	
	//Required for basic instantiation
	public Planet() {
		//do nothing
	}

    public Planet(String planetNode, String planetName) {

        this.planetNode = planetNode;
        this.planetName = planetName;
    }

    public String getPlanetNode() {
		return planetNode;
	}

	public void setPlanetNode(String planetNode) {
		this.planetNode = planetNode;
	}

	public String getPlanetName() {
		return planetName;
	}

	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}

    public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public List<Route> getAvailableRoutes() {
		return availableRoutes;
	}

	public void setAvailableRoutes(List<Route> availableRoutes) {
		this.availableRoutes = availableRoutes;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public Planet getPrevious() {
		return previous;
	}

	public void setPrevious(Planet previous) {
		this.previous = previous;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Planet other = (Planet) obj;
        if (this.planetNode != other.planetNode) {
            return false;
        }
        if (!Objects.equals(this.planetName, other.planetName)) {
            return false;
        }
        return Objects.equals(this.planetNode, other.planetNode);
    }

	@Override
	public int hashCode() {
	int hash = 0;
	hash += (this.planetNode != null ? this.planetNode.hashCode() : 0);
	return hash;
	}
	
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Planet{");
        sb.append("planetNode=").append(planetNode);
        sb.append(", planetName='").append(planetName).append('\'');
        sb.append('}');
        return sb.toString();
    }
  
	@Override
	public int compareTo(Planet otherPlanet) {
		return Double.compare(this.distance, otherPlanet.getDistance());
	}
	
}