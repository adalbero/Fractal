package com.adalbero.app.fractal.functions;

import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.Palette;

public class NewtonZ8 extends Newton {
	private static final int NUM_ROOTS = 8;

	@Override
	public int getNumRoots() {
		return NUM_ROOTS;
	}

	@Override
	public String getFunction() {
		return "f(z) = z^8+15z^4-16";
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.RAINBOW_MOD2;
	}

	@Override
	protected Complex f(Complex z) {
		// z^8 + 15z^4 - 16
		return z.pow(8).plus(z.pow(4).mult(15)).minus(16);
	}

	@Override
	protected Complex df(Complex z) {
		// 8z^7 + 30*z^3
		return z.pow(7).mult(8).plus(z.pow(3).mult(30));
	}

}
