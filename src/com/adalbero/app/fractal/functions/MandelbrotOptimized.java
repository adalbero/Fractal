package com.adalbero.app.fractal.functions;

import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.Result;

public class MandelbrotOptimized extends Mandelbrot {

	@Override
	public String getName() {
		return "Mandelbrot";
	}

	public Result getResult(Complex zn, Complex c) {
		int maxIterations = params.getInt(MAX_ITERATIONS);
		double toleranceRadius = params.getDouble(TOLERANCE_RADIUS);
		double tolerance2 = toleranceRadius * toleranceRadius;

		double a = zn.getRe();
		double b = zn.getIm();
		double temp;

		double ca = c.getRe();
		double cb = c.getIm();
		
		for (int it = 0; it < maxIterations; it++) {

			// if |z| > radius 
			// ==> sqrt(a*a + b*b) > r 
			// ==> a*a + b*b > r*r
			if (a * a + b * b > tolerance2) {
				return new Result(it);
			}

			// f(z, c) = z^2 + c
			// ==> z = (a + bi)
			// ==> c = (ca + cbi)
			// f(z, c) = (a + bi)^2 + (ca + cbi)
			// ==> a = a^2 + (bi)^2 + ca = a^2 - b^2 + ca
			// ==> b = 2 * a * b + cb
			temp = a;
			a = a * a - b * b + ca;
			b = 2 * temp * b + cb;
		}

		return Result.NULL;
	}

}
