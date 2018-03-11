package com.adalbero.app.fractal.functions;

import java.util.ArrayList;
import java.util.List;

import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.ComplexPlane;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Result;

public class Mandelbrot extends Fractal {

	public static String TOLERANCE_RADIUS = "Tolerance Radius";
	public static String MAX_ITERATIONS = "Max Iterations";

	private int maxIterations;
	private double tolerance;

	public Mandelbrot() {
		init();
	}

	@Override
	public String getName() {
		return "Mandelbrot";
	}

	@Override
	public String getFunction() {
		return "f(z) = z^2 + c";
	}

	@Override
	public void init() {
		params.setParam(TOLERANCE_RADIUS, 2d);
		params.setParam(MAX_ITERATIONS, 1e3);
	}

	@Override
	public Plane getPreferedPlane() {
		return new ComplexPlane(new Complex(-0.7, 0), 1.2);
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.METAL;
	}

	@Override
	public double getTolerance() {
		return params.getDouble(TOLERANCE_RADIUS);
	}

	@Override
	public int getMaxIterations() {
		return params.getInt(MAX_ITERATIONS);
	}

	protected Complex f(Complex z, Complex c) {
		return z.pow(2).plus(c);
	}

	public Complex getZ0(Coordinate point) {
		return Complex.ZERO;
	}

	public Complex getC(Coordinate point) {
		return new Complex(point);
	}

	@Override
	public void initResult() {
		super.initResult();

		maxIterations = getMaxIterations();
		tolerance = getTolerance();
	}

	@Override
	public Result getResult(Coordinate point) {
		Complex c = getC(point);
		Complex zn = getZ0(point);

		for (int it = 1; it < maxIterations; it++) {
			Complex zn1 = f(zn, c);

			if (zn1.mod2() > tolerance * tolerance) {
				return new Result(it);
			}

			zn = zn1;
		}

		return Result.NULL;
	}

	@Override
	public List<Coordinate> getIterations(Coordinate point, int n) {
		Complex c = getC(point);
		Complex zn = getZ0(point);

		List<Coordinate> iterations = new ArrayList<>();

		iterations.add(zn);
		for (int it = 0; it < n; it++) {
			Complex zn1 = f(zn, c);
			iterations.add(zn1);
			zn = zn1;
		}

		return iterations;
	}

}
