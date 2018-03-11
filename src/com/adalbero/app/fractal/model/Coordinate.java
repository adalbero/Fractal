package com.adalbero.app.fractal.model;

public class Coordinate {
	private double x, y;

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return this.x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getY() {
		return this.y;
	}

	public Coordinate plus(Coordinate z) {
		double x = this.getX() + z.getX();
		double y = this.getY() + z.getY();

		return new Coordinate(x, y);
	}

	public Coordinate minus(Coordinate z) {
		double x = this.getX() - z.getX();
		double y = this.getY() - z.getY();

		return new Coordinate(x, y);
	}

	public double mod() {
		return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", Util.format(x), Util.format(y));
	}
}
