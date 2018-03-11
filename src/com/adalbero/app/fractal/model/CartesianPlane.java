package com.adalbero.app.fractal.model;

public class CartesianPlane extends Plane {

	public CartesianPlane(Coordinate center, double radius) {
		super(center, radius);
	}

	public String getName() { 
		return "Cartesian Plane";
	}


}
