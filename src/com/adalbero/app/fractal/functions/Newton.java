package com.adalbero.app.fractal.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.ComplexPlane;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.model.Result;

public abstract class Newton extends Fractal {

	public static String TOLERANCE = "Tolerance";
	public static String MAX_ITERATIONS = "Max Iterations";
	public static String ALPHA = "Alpha";
	public static String EPSILON = "Epsilon";

	private int maxIterations;
	private double tolerance;
	private double alpha;
	private Complex cAlpha;
	private double epsilon;

	private List<Complex> roots;
	private boolean discoverRoots;

	public Newton() {
		
		Complex[] knownRoots = getKnownRoots();
		if (knownRoots != null) {
			roots = Arrays.asList(knownRoots);
		} else {
			roots = new ArrayList<>();
		}
		
		discoverRoots = (roots.size() == 0);

		init();
	}

	@Override
	public void init() {
		params.setParam(TOLERANCE, 1e-6);
		params.setParam(MAX_ITERATIONS, 100);
		params.setParam(ALPHA, 1);
		params.setParam(EPSILON, 1e-3);
	}

	@Override
	public ComplexPlane getPreferedPlane() {
		return new ComplexPlane(Complex.ZERO, 2f);
	}

	@Override
	public String getName() {
		return "Newton";
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.RAINBOW_MOD2;
	}

	public abstract Complex f(Complex z);

	public abstract Complex df(Complex z);

	@Override
	public double getTolerance() {
		return params.getDouble(TOLERANCE);
	}

	@Override
	public int getMaxIterations() {
		return params.getInt(MAX_ITERATIONS);
	}

	@Override
	public void prepareToRun() {
		super.prepareToRun();

		maxIterations = getMaxIterations();
		tolerance = getTolerance();

		alpha = params.getDouble(ALPHA);
		cAlpha = new Complex(alpha);

		epsilon = params.getDouble(EPSILON);

		if (discoverRoots) {
			roots.clear();
		}
	}

	@Override
	public Result getResult(Coordinate point) {
		Complex zn = new Complex(point);

		for (int iteration = 1; iteration < maxIterations; iteration++) {

			// zn+1 = zn - alpha * f(z)/f'(z);
			Complex zn1 = zn.minus(cAlpha.mult(f(zn).div(df(zn))));

			Complex y = f(zn1);

			if (y.mod() < tolerance) {
				int rootNum = getRootNum(zn1);
				return new Result(iteration, rootNum);
			}

			zn = zn1;
		}

		return Result.NULL;
	}

	@Override
	public List<Coordinate> getIterations(Coordinate point, int n) {
		Complex zn = new Complex(point);

		List<Coordinate> iterations = new ArrayList<>();

		iterations.add(zn);
		for (int iteration = 0; iteration < n; iteration++) {

			Complex zn1 = zn.minus(cAlpha.mult(f(zn).div(df(zn))));

			iterations.add(zn1);

			zn = zn1;
		}

		return iterations;
	}

	public Complex getRoot(int n) {
		if (n >= 0 && n < roots.size()) {
			return roots.get(n);
		}

		return null;
	}

	protected int getRootNum(Complex c) {
		int r = findRoot(c);

		if (r < 0) {
			if (discoverRoots && roots.size() < 10) {
//				System.out.println("new root: " + roots.size() + " - " + c + " - " + c.mod());
				roots.add(c);
				r = roots.size() - 1;
			} else {
				r = findNearestRoot(c);
			}
		}

		return r;
	}

	protected int findRoot(Complex c) {
		for (int i = 0; i < roots.size(); i++) {
			Complex root = roots.get(i);
			if (c.equals(root, epsilon)) {
				return i;
			}
		}

		return -1;
	}

	protected int findNearestRoot(Complex c) {
		int r = 0;
		double dist = Double.MAX_VALUE;

		for (int i = 0; i < roots.size(); i++) {
			Complex root = roots.get(i);
			if (c.dist(root) < dist) {
				dist = c.dist(root);
				r = i;
			}
		}

		return r;
	}

	public List<Complex> getRoots() {
		return roots;
	}

	@Override
	public int getNumRoots() {
		return getRoots().size();
	}

}
