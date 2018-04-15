package com.adalbero.app.fractal.model;

public class Rotate extends Coordinate {
	public Rotate() {
		super(0, 0);
	}

	public Rotate(Coordinate c) {
		super(c.getX(), c.getY());
	}

	public Rotate(double x) {
		super(x, x);
	}

	public Rotate(double x, double y) {
		super(x, y);
	}
}
