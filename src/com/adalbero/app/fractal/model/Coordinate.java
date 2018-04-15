package com.adalbero.app.fractal.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {
	private double x, y;

	public final static Coordinate ZERO = new Coordinate(0, 0);

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

	public Coordinate plus(Coordinate p) {
		double x = this.getX() + p.getX();
		double y = this.getY() + p.getY();

		return new Coordinate(x, y);
	}

	public Coordinate minus(Coordinate p) {
		double x = this.getX() - p.getX();
		double y = this.getY() - p.getY();

		return new Coordinate(x, y);
	}

	public double mod() {
		return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
	}

	public double dist(Coordinate p) {
		return this.minus(p).mod();
	}

	public boolean equals(Coordinate p, double epslon) {
		return this.dist(p) <= epslon;
	}

	public Coordinate truncate(int precision) {
		double mask = Math.pow(10, precision);

		double x = ((int) (this.getX() * mask) / mask);
		double y = ((int) (this.getY() * mask) / mask);

		return new Coordinate(x, y);
	}

	@Override
	public String toString() {
		return format(true);
	}

	public String format(boolean useParenthesis) {
		if (useParenthesis) {
			return String.format("(%s)", format(false));
		} else {
			return String.format("%s, %s", Util.format(x), Util.format(y));
		}
	}

	public static Coordinate parseCoordinate(String str) {
		str = normalize(str);

		final String PATTERN_DOUBLE = "[-+]?[0-9]*\\.?[0-9]+(?:[eE][-+]?[0-9]+)?";

		Pattern p = Pattern.compile("[(]?(" + PATTERN_DOUBLE + "),(" + PATTERN_DOUBLE + ")[)]?");
		Matcher m = p.matcher(str);

		if (m.find()) {
			String sX = m.group(1);
			String sY = m.group(2);

			double x;
			if (sX == null) {
				x = 0;
			} else {
				x = Double.parseDouble(sX);
			}

			double y;
			if (sY == null) {
				y = 0;
			} else {
				y = Double.parseDouble(sY);
			}

			return new Coordinate(x, y);
		}

		return Coordinate.ZERO;
	}

	private static String normalize(String str) {
		str = str.replaceAll("\\s", "");

		return str;
	}

}
