package com.adalbero.app.fractal.functions;

import com.adalbero.app.fractal.model.Complex;

public class NewtonSin extends Newton {

	@Override
	public String getFunction() {
		return "f(z) = sin(z)";
	}

	@Override
	public Complex f(Complex z) {
		// sin(z)
		return z.sin();
	}

	@Override
	public Complex df(Complex z) {
		// cos(z)
		return z.cos();
	}

}
