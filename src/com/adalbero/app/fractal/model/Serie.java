package com.adalbero.app.fractal.model;

import java.util.ArrayList;

public class Serie extends ArrayList<Double> {

	private static final long serialVersionUID = 1L;

	public double getMaxValue() {
		double max = get(0);
		for (Double v : this) {
			max = Math.max(max, v);
		}

		return max;
	}

	public double getValue(int idx) {
		if (idx < size()) {
			return get(idx);
		} else {
			return 0;
		}
	}
}
