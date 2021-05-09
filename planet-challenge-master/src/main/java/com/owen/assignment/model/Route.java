package com.owen.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Route Entity created to model edges of a Graph
 * Persists data we require to be stored in the db
 *
 */
@Entity
@Table(name = "route")
public class Route {

	@Id
    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "planet_origin")
	private String planetOrigin;
    
    @Column(name = "planet_destination")
	private String planetDestination;
    
    @Column(name = "distance")
    private double distance;

    //Required for basic instantiation
	public Route() {
		//do nothing
	}
	
    public Route(Integer routeId, String planetOrigin, String planetDestination, double distance) {
		this.routeId = routeId;
		this.planetOrigin = planetOrigin;
		this.planetDestination = planetDestination;
		this.distance = distance;
	}
    
	public Integer getRoute() {
		return routeId;
	}

	public String getPlanetOrigin() {
		return planetOrigin;
	}

	public String getPlanetDestination() {
		return planetDestination;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Route{");
        sb.append("routeId=").append(routeId);
        sb.append(", planetOrigin='").append(planetOrigin).append('\'');
        sb.append(", planetDestination='").append(planetDestination).append('\'');
        sb.append(", distance='").append(distance).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
