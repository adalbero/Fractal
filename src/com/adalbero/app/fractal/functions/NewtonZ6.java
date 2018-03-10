package com.adalbero.app.fractal.functions;

import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.Palette;

public class NewtonZ6 extends Newton {
	private static final int NUM_ROOTS = 6;

	@Override
	public int getNumRoots() {
		return NUM_ROOTS;
	}

	@Override
	public String getName() {
		return "Newton z^6+z^3-1";
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.ROOT6;
	}

	@Override
	protected Complex f(Complex z) {
		// z^6 + z^3 - 1
		return z.pow(6).plus(z.pow(3)).minus(1);
	}

	@Override
	protected Complex df(Complex z) {
		// 6z^5 + 3z^2
		return z.pow(5).mult(6).plus(z.pow(2).mult(3));
	}

}
