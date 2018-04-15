package com.adalbero.app.fractal.model;

public class Scale extends Coordinate {
	public Scale() {
		super(1, 1);
	}

	public Scale(Coordinate c) {
		super(c.getX(), c.getY());
	}

	public Scale(double x) {
		super(x, x);
	}

	public Scale(double x, double y) {
		super(x, y);
	}

}
