package com.adalbero.app.fractal.controller;

import java.util.ArrayList;

import com.adalbero.app.fractal.functions.Circle;
import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.functions.IteratedFunction;
import com.adalbero.app.fractal.functions.JuliaSet;
import com.adalbero.app.fractal.functions.Mandelbrot;
import com.adalbero.app.fractal.functions.Newton;
import com.adalbero.app.fractal.functions.NewtonSin;
import com.adalbero.app.fractal.functions.NewtonZ3;
import com.adalbero.app.fractal.functions.NewtonZ3b;
import com.adalbero.app.fractal.functions.NewtonZ5;
import com.adalbero.app.fractal.functions.NewtonZ5b;
import com.adalbero.app.fractal.functions.NewtonZ6;
import com.adalbero.app.fractal.functions.NewtonZ8;
import com.adalbero.app.fractal.model.Complex;

public class FractalList extends ArrayList<Fractal> {

	private static final long serialVersionUID = 1L;

	public FractalList() {

		this.add(new Mandelbrot());
		this.add(new JuliaSet());
		this.add(new NewtonZ3());
		this.add(new NewtonZ3b());
		this.add(new NewtonZ5());
		this.add(new NewtonZ5b());
		this.add(new NewtonZ6());
		this.add(new NewtonZ8());
		this.add(new NewtonSin());

		this.add(new Newton() {
			@Override
			public Complex[] getKnownRoots() {
				return new Complex[] { new Complex(0, 0), new Complex(1, 0), new Complex(1, 0) };
			}

			@Override
			public String getFunction() {
				return "f(z) = z^3 - 2 z^2 + z";
			}

			@Override
			public Complex f(Complex z) {
				// z^3 - 2 z^2 + z
				return z.pow(3).minus(z.pow(2).mult(2)).plus(z);
			}

			@Override
			public Complex df(Complex z) {
				// 3 z^2 - 4 z + 1
				return z.pow(2).mult(3).minus(z.mult(4)).plus(1);
			}
		});

		this.add(new Newton() {
			// @Override
			// public Complex[] getKnownRoots() {
			// return new Complex[] { new Complex(0, 0), new Complex(1, 0) };
			// }

			@Override
			public String getFunction() {
				return "f(z) = z^3 - 2 z^2 + z - 0.1";
			}

			@Override
			public Complex f(Complex z) {
				// z^3 - 2 z^2 + z + 1
				return z.pow(3).minus(z.pow(2).mult(2)).plus(z).minus(0.1);
			}

			@Override
			public Complex df(Complex z) {
				// 3 z^2 - 4 z + 1
				return z.pow(2).mult(3).minus(z.mult(4)).plus(1);
			}
		});

		this.add(new Newton() {
			@Override
			public Complex[] getKnownRoots() {
				return new Complex[] { new Complex(0, 0), new Complex(1, 0), new Complex(-1, 0) };
			}

			@Override
			public String getFunction() {
				return "f(z) = z^3 - z";
			}

			@Override
			public Complex f(Complex z) {
				// z^3 - z
				return z.pow(3).minus(z);
			}

			@Override
			public Complex df(Complex z) {
				// 3 z^2 - 1
				return z.pow(2).mult(3).minus(1);
			}
		});

		this.add(new Newton() {
			@Override
			public String getFunction() {
				return "f(z) = z^3";
			}

			@Override
			public Complex f(Complex z) {
				// z^3
				return z.pow(3);
			}

			@Override
			public Complex df(Complex z) {
				// 3 z^21
				return z.pow(2).mult(3);
			}
		});

		this.add(new IteratedFunction());

		this.add(new Circle());

	}

}
