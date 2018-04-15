package com.adalbero.app.fractal.model;

import java.util.ArrayList;
import java.util.List;

public class Transformation {
	private double a, b, c, d, e, f;
	private Scale scale = new Scale(1);
	private Rotate rotate = new Rotate(0);
	private Translate translate = new Translate(0);

	public Transformation(double a, double b, double c, double d, Translate t) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = t.getX();
		this.f = t.getY();
	}

	public Transformation(Scale s, Rotate r, Translate t) {
		set(s, r, t);
	}

	public void set(Scale s, Rotate r, Translate t) {
		double alphaX = r.getX() * 2 * Math.PI / 360;
		double alphaY = r.getY() * 2 * Math.PI / 360;

		this.scale = s;
		this.rotate = r;
		this.translate = t;

		this.a = s.getX() * Math.cos(alphaX);
		this.b = s.getX() * Math.sin(alphaX);

		this.c = s.getY() * -Math.sin(alphaY);
		this.d = s.getY() * Math.cos(alphaY);

		this.e = t.getX();
		this.f = t.getY();
	}

	public Scale getScale() {
		return this.scale;
	}

	public Rotate getRotate() {
		return this.rotate;
	}

	public Translate getTranslate() {
		return this.translate;
	}

	public Coordinate transform(Coordinate p) {
		// double x = a * p.getX() + b * p.getY() + e;
		// double y = c * p.getX() + d * p.getY() + f;
		// return new Coordinate(x, y);

		p = scale(p);
		p = rotate(p);
		p = translate(p);

		return p;
	}

	public Coordinate scale(Coordinate p) {
		double x = scale.getX() * p.getX();
		double y = scale.getY() * p.getY();

		return new Coordinate(x, y);
	}

	public Coordinate rotate(Coordinate p) {
		double alphaX = rotate.getX() * 2 * Math.PI / 360;
		double alphaY = rotate.getY() * 2 * Math.PI / 360;

		double x = Math.cos(alphaX) * p.getX() + Math.sin(alphaY) * p.getY();
		double y = -Math.sin(alphaX) * p.getX() + Math.cos(alphaY) * p.getY();

		return new Coordinate(x, y);
	}

	public Coordinate translate(Coordinate p) {
		double x = translate.getX() + p.getX();
		double y = translate.getY() + p.getY();

		return new Coordinate(x, y);
	}

	public List<Coordinate> transform(List<Coordinate> points) {
		List<Coordinate> list = new ArrayList<>();

		for (Coordinate p : points) {
			list.add(this.transform(p));
		}

		return list;
	}

}
