package com.adalbero.app.fractal.functions;

import java.util.ArrayList;
import java.util.List;

import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.ComplexPlane;
import com.adalbero.app.fractal.data.Palette;
import com.adalbero.app.fractal.data.Result;

public class Mandelbrot extends Fractal {

	public static String TOLERANCE_RADIUS = "Tolerance Radius";
	public static String MAX_ITERATIONS = "Max Iterations";

	private int maxIterations;
	private double tolerance;

	public Mandelbrot() {
		init();
	}

	@Override
	public void init() {
		params.setParam(TOLERANCE_RADIUS, 2d);
		params.setParam(MAX_ITERATIONS, 1e3);
	}

	@Override
	public ComplexPlane getPreferedComplexPlane() {
		return new ComplexPlane(new Complex(-0.7, 0), 1.2);
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.GRADIENT;
	}

	@Override
	public String getName() {
		return "Mandelbrot";
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

	public Complex getZ0(Complex point) {
		return Complex.ZERO;
	}
	
	public Complex getC(Complex point) {
		return point;
	}
	
	@Override
	public void initResult() {
		super.initResult();

		maxIterations = getMaxIterations();
		tolerance = getTolerance();
	}

	@Override
	public Result getResult(Complex point) {
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
	public List<Complex> getIterations(Complex point, int n) {
		Complex c = getC(point);
		Complex zn = getZ0(point);

		List<Complex> iterations = new ArrayList<>();

		iterations.add(zn);
		for (int it = 0; it < n; it++) {
			Complex zn1 = f(zn, c);
			iterations.add(zn1);
			zn = zn1;
		}

		return iterations;
	}

}
