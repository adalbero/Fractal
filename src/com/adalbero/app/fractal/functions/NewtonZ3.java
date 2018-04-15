package com.adalbero.app.fractal.functions;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.Palette;

public class NewtonZ3 extends Newton {

	@Override
	public String getFunction() {
		return "f(z) = z^3-1";
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.ROOT3;
	}

	@Override
	public Complex f(Complex z) {
		// z^3 - 1
		return z.pow(3).minus(1);
	}

	@Override
	public Complex df(Complex z) {
		// 3z^2
		return z.pow(2).mult(3);
	}

}
