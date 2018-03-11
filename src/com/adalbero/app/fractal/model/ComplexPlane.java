package com.adalbero.app.fractal.model;

public class ComplexPlane extends Plane {

	public ComplexPlane(Complex center, double radius) {
		super(center, radius);
	}

	public Complex getComplexCenter() {
		return new Complex(center);
	}

	public Complex getComplexTarget() {
		return new Complex(target);
	}

	public Complex getComplexPoint(int x, int y) {
		return new Complex(getX(x), getY(y));
	}

	@Override
	public String getName() {
		return "Complex Plane";
	}

	@Override
	public String toString() {
		return String.format("%s: {center:[%s] radius:%s} ", getName(), getComplexCenter().format(true),
				Util.format(radius));
	}

}
