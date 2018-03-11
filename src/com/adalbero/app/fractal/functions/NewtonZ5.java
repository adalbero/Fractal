package com.adalbero.app.fractal.functions;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.Palette;

public class NewtonZ5 extends Newton {
	private static final int NUM_ROOTS = 5;

	@Override
	public int getNumRoots() {
		return NUM_ROOTS;
	}

	@Override
	public String getFunction() {
		return "f(z) = z^5-1";
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.ROOT5;
	}

	@Override
	protected Complex f(Complex z) {
		// z^5 - 1
		return z.pow(5).minus(1);
	}

	@Override
	protected Complex df(Complex z) {
		// 5z^4
		return z.pow(4).mult(5);
	}

}
