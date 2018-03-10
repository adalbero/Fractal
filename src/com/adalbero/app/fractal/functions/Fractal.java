package com.adalbero.app.fractal.functions;

import java.util.List;

import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.ComplexPlane;
import com.adalbero.app.fractal.data.Palette;
import com.adalbero.app.fractal.data.Parameters;
import com.adalbero.app.fractal.data.Result;
import com.adalbero.app.fractal.data.Util;

public abstract class Fractal {
	protected Parameters params = new Parameters();

	public abstract ComplexPlane getPreferedComplexPlane();

	public abstract Palette.Name getPreferedPalette();

	public abstract String getName();

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

	public abstract Result getResult(Complex point);

	public abstract List<Complex> getIterations(Complex point, int n);

	@Override
	public String toString() {
		String msg = getName() + ": {";

		for (String key : params.getKeys()) {
			double value = params.getDouble(key);
			msg += String.format("%s:%s ", key, Util.format(value));
		}

		msg += "} ";

		return msg;
	}

}
