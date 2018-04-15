package com.adalbero.app.fractal.model;

public class Translate extends Coordinate {
	public Translate() {
		super(0, 0);
	}

	public Translate(Coordinate c) {
		super(c.getX(), c.getY());
	}

	public Translate(double x) {
		super(x, x);
	}
	public Translate(double x, double y) {
		super(x, y);
	}
}
