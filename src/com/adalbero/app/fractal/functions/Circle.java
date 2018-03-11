package com.adalbero.app.fractal.functions;

import java.util.ArrayList;
import java.util.List;

import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.ComplexPlane;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.model.Result;

public class Circle extends Fractal {

	@Override
	public String getName() {
		return "Circle";
	}

	@Override
	public String getFunction() {
		return "f(x) = |z|";
	}

	@Override
	public Result getResult(Coordinate point) {
		Complex z = new Complex(point);
		double mod = z.mod();

		int idx = (int) mod;
		return new Result(idx);
	}

	@Override
	public List<Coordinate> getIterations(Coordinate point, int n) {
		return new ArrayList<Coordinate>();
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
	public ComplexPlane getPreferedPlane() {
		return new ComplexPlane(Complex.ZERO, 10);
	}

	@Override
	public Palette.Name getPreferedPalette() {
		return Palette.Name.RAINBOW_MOD2;
	}

}
