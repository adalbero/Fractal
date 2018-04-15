package com.adalbero.app.fractal.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Complex extends Coordinate {
	// private double real;
	// private double imaginary;

	public static final Complex ZERO = new Complex(0);
	public static final Complex ONE = new Complex(1);

	public Complex() {
		super(0, 0);
	}

	public Complex(double re, double im) {
		super(re, im);
	}

	public Complex(double re) {
		super(re, 0);
	}

	public Complex(Complex z) {
		super(z.getRe(), z.getIm());
	}

	public Complex(Coordinate point) {
		super(point.getX(), point.getY());
	}

	public double getRe() {
		return getX();
	}

	public double getIm() {
		return getY();
	}

	public Complex plus(double x) {
		return plus(new Complex(x));
	}

	public Complex plus(Complex z) {
		double re = this.getRe() + z.getRe();
		double im = this.getIm() + z.getIm();

		return new Complex(re, im);
	}

	public Complex minus(double x) {
		return minus(new Complex(x));
	}

	public Complex minus(Complex z) {
		double re = this.getRe() - z.getRe();
		double im = this.getIm() - z.getIm();

		return new Complex(re, im);
	}

	public Complex mult(double x) {
		return mult(new Complex(x));
	}

	public Complex mult(Complex z) {
		double re = this.getRe() * z.getRe() - this.getIm() * z.getIm();
		double im = this.getRe() * z.getIm() + this.getIm() * z.getRe();

		return new Complex(re, im);
	}

	public Complex div(double x) {
		return div(new Complex(x));
	}

	public Complex div(Complex z) {
		Complex output = this.mult(z.conjugate());
		double div = z.mod2();

		return new Complex(output.getRe() / div, output.getIm() / div);
	}

	public Complex pow(int power) {
		Complex output = new Complex(this);
		for (int i = 1; i < power; i++) {
			output = this.mult(output);
		}

		return output;
	}

	public Complex exp() {
		double re = this.getRe();
		double im = this.getIm();
		double r = Math.exp(re);
		re = r * Math.cos(im);
		im = r * Math.sin(im);

		return new Complex(re, im);
	}

	public Complex sin() {
		double x = Math.exp(this.getIm());
		double x_inv = 1 / x;
		double re = Math.sin(this.getRe()) * (x + x_inv) / 2;
		double im = Math.cos(this.getRe()) * (x - x_inv) / 2;

		return new Complex(re, im);
	}

	public Complex cos() {
		double x = Math.exp(this.getIm());
		double x_inv = 1 / x;
		double re = Math.cos(this.getRe()) * (x + x_inv) / 2;
		double im = -Math.sin(this.getRe()) * (x - x_inv) / 2;

		return new Complex(re, im);
	}

	public Complex tan() {
		return this.sin().div(this.cos());
	}

	public Complex cot() {
		return ONE.div(this.tan());
	}

	public Complex sec() {
		return ONE.div(this.cos());
	}

	public Complex cosec() {
		return ONE.div(this.sin());
	}

	public double getArg() {
		return Math.atan2(this.getIm(), this.getRe());
	}

	public Complex inverse() {
		return ONE.div(this);
	}

	public Complex conjugate() {
		return new Complex(this.getRe(), -this.getIm());
	}

	public double mod() {
		return Math.sqrt(this.mod2());
	}

	public double mod2() {
		return Math.pow(this.getRe(), 2) + Math.pow(this.getIm(), 2);
	}

	public Complex square() {
		double re = this.getRe() * this.getRe() - this.getIm() * this.getIm();
		double im = 2 * this.getRe() * this.getIm();

		return new Complex(re, im);
	}

	@Override
	public final boolean equals(Object o) {
		if (o instanceof Complex) {
			Complex z = (Complex) o;
			return (this.getRe() == z.getRe()) && (this.getIm() == z.getIm());
		}

		return false;
	}

	@Override
	public String toString() {
		return format(false);
	}

	public String format(boolean forceImg) {
		String re = Util.format(this.getRe());
		String im = "";

		if (forceImg || this.getIm() != 0.0) {
			if (this.getIm() >= 0)
				im += "+";

			if (this.getIm() == 1) {
				im += "i";
			} else if (this.getIm() == -1) {
				im += "-i";
			} else {
				im += Util.format(this.getIm()) + "i";
			}
		}

		return (re + " " + im).trim();
	}

	public static Complex parseComplex(String str) {
		str = normalize(str);

		Pattern p = Pattern.compile("([+-]?[0-9.]+(?:[eE][+-]?\\d+)?)?(?:([+-]?[0-9.]*(?:[eE][+-]?\\\\d+)?)i)?");
		Matcher m = p.matcher(str);

		if (m.find()) {
			String sRe = m.group(1);
			String sIm = m.group(2);

			double re;
			if (sRe == null) {
				re = 0;
			} else {
				re = Double.parseDouble(sRe);
			}
			
			double im;
			if (sIm == null)  {
				im = 0;
			} else if (sIm.length() == 0) {
				if (sRe != null) {
					im = re;
					re = 0;
				} else {
					im = 1;
				}
			} else if (sIm.equals("+")) {
				im = 1;
			} else if (sIm.equals("-")) {
				im = -1;
			} else {
				im = Double.parseDouble(sIm);
			}

			return new Complex(re, im);
		}

		return Complex.ZERO;
	}

	private static String normalize(String str) {
		str = str.replaceAll("\\s", "");

		for (String key : mathConstants.keySet()) {
			String value = String.format(Locale.US, "%.3f", mathConstants.get(key));
			str = str.replaceAll(key, value);
		}

		return str;
	}

	private static Map<String, Double> mathConstants = new HashMap<>();

	static {
		mathConstants.put("pi", Math.PI);
		mathConstants.put("golden", 1 + Math.sqrt(5) / 2);
	}
}
