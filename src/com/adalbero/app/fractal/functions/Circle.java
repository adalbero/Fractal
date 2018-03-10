package com.adalbero.app.fractal.functions;

import java.util.ArrayList;
import java.util.List;

import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.ComplexPlane;
import com.adalbero.app.fractal.data.Palette;
import com.adalbero.app.fractal.data.Result;

public class Circle extends Fractal {

	@Override
	public Result getResult(Complex point) {
		double mod = point.mod();

		int idx = (int) mod;
		return new Result(idx);
	}

	public List<Complex> getIterations(Complex point, int n) {
		return new ArrayList<Complex>();
	}

	@Override
	public double getTolerance() {
		return 10;
	}

	@Override
	public int getMaxIterations() {
		return 1;
	}

	@Override
	public String getName() {
		return "Test Fractal";
	}

	@Override
	public ComplexPlane getPreferedComplexPlane() {
		return new ComplexPlane(Complex.ZERO, 10);
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.RAINBOW_2;
	}

}
