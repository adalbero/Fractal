package com.adalbero.app.fractal.functions;

import java.util.ArrayList;
import java.util.List;

import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.ComplexPlane;
import com.adalbero.app.fractal.data.Palette;
import com.adalbero.app.fractal.data.Result;

public abstract class Newton extends Fractal {

	public static String TOLERANCE = "tolerance";
	public static String MAX_ITERATIONS = "maxIterations";
	public static String ALPHA = "alpha";

	private int maxIterations;
	private double tolerance;
	private double alpha;
	private Complex cAlpha;

	private List<Complex> roots = new ArrayList<>();

	public Newton() {
		init();
	}

	@Override
	public void init() {
		params.setParam(TOLERANCE, 1e-6);
		params.setParam(MAX_ITERATIONS, 100);
		params.setParam(ALPHA, 1);
	}

	@Override
	public ComplexPlane getPreferedComplexPlane() {
		return new ComplexPlane(Complex.ZERO, 2f);
	}

	@Override
	public String getName() {
		return "Newton";
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.RAINBOW_2;
	}

	protected abstract Complex f(Complex z);

	protected abstract Complex df(Complex z);

	@Override
	public double getTolerance() {
		return params.getDouble(TOLERANCE);
	}

	@Override
	public int getMaxIterations() {
		return params.getInt(MAX_ITERATIONS);
	}

	@Override
	public void initResult() {
		maxIterations = getMaxIterations();
		tolerance = getTolerance();

		alpha = params.getDouble(ALPHA);
		cAlpha = new Complex(alpha);
	}

	@Override
	public Result getResult(Complex zn) {

		for (int iteration = 1; iteration < maxIterations; iteration++) {
			// zn+1 = zn - alpha * f(z)/f'(z);
			Complex zn1 = zn.minus(cAlpha.mult(f(zn).div(df(zn))));

			Complex zz = f(zn1);

			if (zz.mod() < tolerance) {
				int root = getRoot(zn1, tolerance);
				return new Result(iteration, root);
			}

			zn = zn1;
		}

		return Result.NULL;
	}

	@Override
	public List<Complex> getIterations(Complex zn, int n) {
		List<Complex> iterations = new ArrayList<>();

		iterations.add(zn);
		for (int iteration = 0; iteration < n; iteration++) {
			Complex zn1 = zn.minus(cAlpha.mult(f(zn).div(df(zn))));

			iterations.add(zn1);
			
			zn = zn1;
		}

		return iterations;
	}

	private int getRoot(Complex c, double tolerance) {
		for (int i = 0; i < roots.size(); i++) {
			if (c.equals(roots.get(i), tolerance)) {
				return i + 1;
			}
		}

		roots.add(c);
		return roots.size();
	}

	public List<Complex> getRoots() {
		return roots;
	}
}
