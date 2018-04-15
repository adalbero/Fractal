package com.adalbero.app.fractal.functions;

import com.adalbero.app.fractal.model.Complex;

public class NewtonZ3b extends NewtonZ3 {

	@Override
	public String getFunction() {
		return "f(z) = z^3-2z+2";
	}

	@Override
	public Complex f(Complex z) {
		// z^3 - 2z + 2
		return z.pow(3).minus(z.mult(2)).plus(2);
	}

	@Override
	public Complex df(Complex z) {
		// 3z^2 -2
		return z.pow(2).mult(3).minus(2);
	}

}
