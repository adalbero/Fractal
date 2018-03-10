package com.adalbero.app.fractal.functions;

import com.adalbero.app.fractal.data.Complex;

public class NewtonSin extends Newton {
	private static final int NUM_ROOTS = 6;

	@Override
	public int getNumRoots() {
		return NUM_ROOTS;
	}

	@Override
	public String getName() {
		return "Newton sin(z)";
	}

	@Override
	protected Complex f(Complex z) {
		// sin(z)
		return z.sin();
	}

	@Override
	protected Complex df(Complex z) {
		// cos(z)
		return z.cos();
	}

}
