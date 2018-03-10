package com.adalbero.app.fractal.functions;

import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.ComplexPlane;
import com.adalbero.app.fractal.data.Palette;

public class JuliaSet extends Mandelbrot {

	public static String C = "C";

	private Complex valueC;

	public JuliaSet() {
		init();
	}

	@Override
	public void init() {
		params.setParam(TOLERANCE_RADIUS, 2d);
		params.setParam(MAX_ITERATIONS, 1000);
		params.setParam(C, "-0.8 +0.156i");
	}

	@Override
	public String getName() {
		return "JuliaSet";
	}

	@Override
	public ComplexPlane getPreferedComplexPlane() {
		return new ComplexPlane(Complex.ZERO, 2f);
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.RAINBOW;
	}

	@Override
	public void initResult() {
		super.initResult();

		valueC = getParams().getComplex(C);
	}

	public Complex getZ0(Complex point) {
		return point;
	}

	public Complex getC(Complex point) {
		return valueC;
	}

}
