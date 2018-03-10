package com.adalbero.app.fractal.data;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Parameters {
	private Map<String, Complex> params = new HashMap<>();

	public void setParam(String key, Complex value) {
		params.put(key, value);
	}

	public void setParam(String key, double value) {
		params.put(key, new Complex(value));
	}

	public void setParam(String key, String value) {
		try {
			Complex v = Complex.parseComplex(value);
			params.put(key, v);
		} catch (NumberFormatException ex) {
			System.out.println("Err: not a double: " + value);
		}
	}

	public Complex getComplex(String key) {
		Complex value = params.get(key);

		return (key == null ? new Complex(0) : value);
	}

	public double getDouble(String key) {
		return getComplex(key).getRe();
	}

	public int getInt(String key) {
		return (int) getDouble(key);
	}

	public Set<String> getKeys() {
		return params.keySet();
	}
}
