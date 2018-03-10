package com.adalbero.app.fractal.functions;

import com.adalbero.app.fractal.data.Complex;

public class NewtonTest extends Newton {
	private static final int NUM_ROOTS = 2;

	@Override
	public int getNumRoots() {
		return NUM_ROOTS;
	}

	@Override
	public String getName() {
		return "Newton z^2-1";
	}

	@Override
	protected Complex f(Complex z) {
		// z^2 - 1
		return z.pow(2).minus(1);
	}

	@Override
	protected Complex df(Complex z) {
		// 2z
		return z.mult(2);
	}

}
