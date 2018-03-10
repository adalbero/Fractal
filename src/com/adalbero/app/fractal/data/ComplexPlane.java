package com.adalbero.app.fractal.data;

public class ComplexPlane {
	private int canvasWidth;
	private int canvasHeight;

	private Complex original;
	private double originalRadius;

	private Complex center;
	private Complex offset;
	private double radius;
	private double factor;

	public ComplexPlane(Complex center, double radius) {
		this.original = center;
		this.originalRadius = radius;

		this.radius = radius;

		moveTo(original);
	}

	public void setCanvasSize(int w, int h) {
		this.canvasWidth = w;
		this.canvasHeight = h;

		calcFactor();
	}

	public void reset() {
		this.radius = originalRadius;
		moveTo(original);

		calcFactor();
	}

	private void calcFactor() {
		if (canvasWidth * canvasHeight > 0) {
			factor = 2 * radius / Math.min(canvasWidth, canvasHeight);

			double re = center.getRe() - (canvasWidth * factor) / 2;
			double im = center.getIm() - (canvasHeight * factor) / 2;

			offset = new Complex(re, im);
		}
	}

	public Complex getPoint(int x, int y) {
		y = canvasHeight - y - 1;
		double re = x * factor + offset.getRe();
		double im = y * factor + offset.getIm();

		return new Complex(re, im);
	}

	public double getRadius() {
		return this.radius;
	}
	
	public void setCoordinates(Complex center, double radius) {
		this.radius = radius;
		moveTo(center);
	}

	public int getCanvasWidth() {
		return this.canvasWidth;
	}

	public int getCanvasHeight() {
		return this.canvasHeight;
	}

	public Complex getCenter() {
		return this.center;
	}
	
	public void moveTo(Complex point) {
		center = point;

		calcFactor();
	}

	public void moveBy(Complex delta) {
		moveTo(center.plus(delta));
	}

	public void zoom(double dRadius) {
		radius *= dRadius;

		calcFactor();
	}

	@Override
	public String toString() {
		return String.format("Plane: {center:[%s] radius:%s} ", center.format(true), Util.format(radius));
	}

}
