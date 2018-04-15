package com.adalbero.app.fractal.functions;

import com.adalbero.app.fractal.model.Complex;

public class NewtonZ5b extends NewtonZ5 {
	@Override
	public String getFunction() {
		return "f(z) = z^5-3iz^3-(5+2i)z^2+3z+1";
	}

	private Complex im3 = new Complex(0, 3);
	private Complex re5im2 = new Complex(5, 2);

	@Override
	public Complex f(Complex z) {
		// z^5 - 3iz^3 - (5+2i)z^2 + 3z + 1
		return z.pow(5).minus(z.pow(3).mult(im3)).minus(z.pow(2).mult(re5im2)).plus(z.mult(3)).plus(1);
	}

	@Override
	public Complex df(Complex z) {
		// 5z^4 - 3.3iz^2 - 2.(5+2i)z + 3
		return z.pow(4).mult(5).minus(z.pow(2).mult(3).mult(im3)).minus(z.mult(2).mult(re5im2)).plus(3);
	}

}
