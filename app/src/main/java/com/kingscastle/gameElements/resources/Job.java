package com.kingscastle.gameElements.resources;


import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.movement.pathing.Path;

public class Job
{
	private Workable resource;
	private Path pathToResource;
	private Path pathToDepot;
	private Building depot;

	public Job( Workable resource , Path pathToResource , Path pathToDepot , Building depot )
	{
		this.resource = resource;
		this.pathToResource = pathToResource;
		this.pathToDepot = pathToDepot;
		this.depot = depot;
	}

	public Workable getResource() {
		return resource;
	}
	public void setResource(Workable resource) {
		this.resource = resource;
	}
	public Path getPathToResource() {
		return pathToResource;
	}
	public void setPathToResource(Path pathToResource) {
		this.pathToResource = pathToResource;
	}
	public Path getPathToDepot() {
		return pathToDepot;
	}
	public void setPathToDepot(Path pathToDepot) {
		this.pathToDepot = pathToDepot;
	}

	public Building getDepot() {
		return depot;
	}

	public void setDepot(Building depot) {
		this.depot = depot;
	}

	public boolean isDone() {
		return resource != null ? resource.isDone()||resource.isDead() : true ;
	}
}
