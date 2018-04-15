package com.adalbero.app.fractal.model;

import java.util.Locale;

public class Util {
	public static String format(double value) {
		if (value == 0) {
			return String.format(Locale.US, "0");
		} else if (value == (int) value) {
			return String.format(Locale.US, "%.0f", value);
		} else {
			return Double.toString(value);
		}
		
		// if (value == 0) {
		// return String.format(Locale.US, "0");
		// } else if (value == (int)value) {
		// return String.format(Locale.US, "%.0f", value);
		// } else if (Double.toString(Math.abs(value)).length() < 8) {
		// return Double.toString(value);
		// } else if (Math.abs(value) > 0.01 && Math.abs(value) < 1000) {
		// return String.format(Locale.US, "%.6f", value);
		// } else {
		// return String.format(Locale.US, "%.2e", value);
		// }
	}
}
