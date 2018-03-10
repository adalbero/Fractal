package com.adalbero.app.fractal.data;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Complex {
	private double real;
	private double imaginary;

	public static Complex ZERO = new Complex(0);
	public static Complex ONE = new Complex(1);

	public Complex() {
		real = 0.0;
		imaginary = 0.0;
	}

	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
	}

	public Complex(double re) {
		this.real = re;
		this.imaginary = 0;
	}

	public Complex(Complex z) {
		this.real = z.real;
		this.imaginary = z.imaginary;
	}

	public double getRe() {
		return this.real;
	}

	public double getIm() {
		return this.imaginary;
	}

	public Complex plus(double x) {
		return plus(new Complex(x));
	}

	public Complex plus(Complex z) {
		double re = this.real + z.real;
		double im = this.imaginary + z.imaginary;

		return new Complex(re, im);
	}

	public Complex minus(double x) {
		return minus(new Complex(x));
	}

	public Complex minus(Complex z) {
		double re = this.real - z.real;
		double im = this.imaginary - z.imaginary;

		return new Complex(re, im);
	}

	public Complex mult(double x) {
		return mult(new Complex(x));
	}

	public Complex mult(Complex z) {
		double re = this.real * z.real - this.imaginary * z.imaginary;
		double im = this.real * z.imaginary + this.imaginary * z.real;

		return new Complex(re, im);
	}

	public Complex div(double x) {
		return div(new Complex(x));
	}

	public Complex div(Complex z) {
		Complex output = this.mult(z.conjugate());
		double div = Math.pow(z.mod(), 2);

		return new Complex(output.real / div, output.imaginary / div);
	}

	public Complex pow(int power) {
		Complex output = new Complex(this);
		for (int i = 1; i < power; i++) {
			double re = output.real * this.real - output.imaginary * this.imaginary;
			double im = output.real * this.imaginary + output.imaginary * this.real;
			output = new Complex(re, im);
		}

		return output;
	}

	public Complex exp() {
		double re = this.real;
		double im = this.imaginary;
		double r = Math.exp(re);
		re = r * Math.cos(im);
		im = r * Math.sin(im);

		return new Complex(re, im);
	}

	public Complex sin() {
		double x = Math.exp(this.imaginary);
		double x_inv = 1 / x;
		double re = Math.sin(this.real) * (x + x_inv) / 2;
		double im = Math.cos(this.real) * (x - x_inv) / 2;

		return new Complex(re, im);
	}

	public Complex cos() {
		double x = Math.exp(this.imaginary);
		double x_inv = 1 / x;
		double re = Math.cos(this.real) * (x + x_inv) / 2;
		double im = -Math.sin(this.real) * (x - x_inv) / 2;
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
		return Math.atan2(imaginary, real);
	}

	public Complex inverse() {
		return ONE.div(this);
	}

	public Complex conjugate() {
		return new Complex(this.real, -this.imaginary);
	}

	public double mod() {
		return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
	}

	public double mod2() {
		return Math.pow(this.real, 2) + Math.pow(this.imaginary, 2);
	}

	public Complex square() {
		double re = this.real * this.real - this.imaginary * this.imaginary;
		double im = 2 * this.real * this.imaginary;

		return new Complex(re, im);
	}

	public final boolean equals(Complex z, double tolerance) {
		return this.minus(z).mod() < tolerance;
	}

	@Override
	public final boolean equals(Object o) {
		if (o instanceof Complex) {
			Complex z = (Complex) o;
			return (this.real == z.real) && (this.imaginary == z.imaginary);
		}

		return false;
	}

	@Override
	public String toString() {
		return format(false);
	}

	public String format(boolean forceImg) {
		String re = Util.format(this.real);
		String im = "";

		if (forceImg || this.imaginary != 0.0) {
			if (this.imaginary >= 0)
				im += "+";

			if (this.imaginary == 1) {
				im += "+i";
			} else if (this.imaginary == -1) {
					im += "-i";
			} else {
				im += Util.format(this.imaginary) + "i";
			}
		}

		return re + " " + im;
	}

	public static Complex parseComplex(String str) {
		str = normalize(str);

		Pattern p = Pattern.compile("([+-]?[0-9.]+(?:[eE][+-]?\\d+)?)?(?:([+-][0-9.]*(?:[eE][+-]?\\\\d+)?)i)?");
		Matcher m = p.matcher(str);

		if (m.find()) {
			String sRe = m.group(1);
			String sIm = m.group(2);
			
			double re = sRe == null ? 0 : Double.parseDouble(sRe);
			double im = sIm == null ? 0 : sIm.length() == 1 ? Double.parseDouble(sIm + "1") : Double.parseDouble(sIm);

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
