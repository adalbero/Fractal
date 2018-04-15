package com.adalbero.app.fractal.model;

public abstract class Plane {
	protected int canvasWidth;
	protected int canvasHeight;

	protected Coordinate center;
	protected double radius;
	protected Coordinate target;

	protected Coordinate defaultCenter;
	protected double defaultRadius;

	protected double offsetX, offsetY;
	protected double factor;

	public Plane(Coordinate center, double radius) {
		this.defaultCenter = center;
		this.defaultRadius = radius;

		reset();
	}
	
	public String getName() { 
		return "Generic Plane";
	}

	public void setCanvasSize(int w, int h) {
		this.canvasWidth = w;
		this.canvasHeight = h;

		calcFactor();
	}

	public void setCenter(Coordinate center) {
		this.center = center;
		
		calcFactor();
	}

	public void setRadius(double radius) {
		this.radius = radius;
		
		calcFactor();
	}

	public void setTarget(Coordinate target) {
		if (target == null) {
			target = this.center;
		}
		
		this.target = target;
	}

	public void reset() {
		setCenter(defaultCenter);
		setRadius(defaultRadius);
		setTarget(defaultCenter);
	}

	protected void calcFactor() {
		if (canvasWidth * canvasHeight > 0) {
			factor = 2 * radius / Math.min(canvasWidth, canvasHeight);

			offsetX = (getCenterX() - (canvasWidth * factor) / 2);
			offsetY = (getCenterY() - (canvasHeight * factor) / 2);
		}
	}

	public double getX(int i) {
		return i * factor + offsetX;
	}

	public double getY(int j) {
		j = canvasHeight - 1 - j;

		return j * factor + offsetY;
	}

	public int getCanvasX(double x) {
		return (int) ((x - offsetX) / factor);
	}

	public int getCanvasY(double y) {
		int j = (int) ((y - offsetY) / factor);
		j = canvasHeight - 1 - j;

		return j;
	}

	public Coordinate getCenter() {
		return this.center;
	}

	public double getRadius() {
		return this.radius;
	}

	public Coordinate getTarget() {
		return this.target;
	}

	public int getCanvasWidth() {
		return this.canvasWidth;
	}

	public int getCanvasHeight() {
		return this.canvasHeight;
	}

	public double getCenterX() {
		return center.getX();
	}

	public double getCenterY() {
		return center.getY();
	}

	public Coordinate getPoint(int i, int j) {
		return new Coordinate(getX(i), getY(j));
	}

	public void moveTo(Coordinate point) {
		setCenter(point);
	}

	public void moveBy(Coordinate delta) {
		moveTo(center.plus(delta));
	}
	
	public void moveToTarget() {
		moveTo(this.target);
	}

	public void zoom(double dRadius) {
		radius *= dRadius;

		moveToTarget();
//		calcFactor();
	}

	@Override
	public String toString() {
		return String.format("%s: {center:[%s] radius:%s} ", getName(), center.toString(), Util.format(radius));
	}

}
