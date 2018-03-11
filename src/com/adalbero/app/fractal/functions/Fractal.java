package com.adalbero.app.fractal.functions;

import java.util.List;

import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.model.Parameters;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Result;

public abstract class Fractal {
	protected Parameters params = new Parameters();

	public abstract Plane getPreferedPlane();

	public abstract Palette.Name getPreferedPalette();

	public abstract String getName();

	public abstract String getFunction();

	public void init() {
	}

	public void reset() {
		init();
	}

	public int getNumRoots() {
		return 0;
	}

	public Parameters getParams() {
		return params;
	}

	public List<Complex> getRoots() {
		return null;
	}

	public abstract double getTolerance();

	public abstract int getMaxIterations();

	public void initResult() {
	}

	public abstract Result getResult(Coordinate point);

	public abstract List<Coordinate> getIterations(Coordinate point, int n);

	@Override
	public String toString() {
		return String.format("%s (%s)", getName(), getFunction());
	}

}
