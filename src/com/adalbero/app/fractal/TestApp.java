package com.adalbero.app.fractal;

import com.adalbero.app.fractal.data.Complex;

public class TestApp {
	public static void main(String[] args) {
		Complex c1 = Complex.parseComplex("-0.4-0.6i");
		System.out.println(c1);
	}
}
