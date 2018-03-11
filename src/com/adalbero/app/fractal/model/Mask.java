package com.adalbero.app.fractal.model;

public class Mask {
	private Result[][] mask;

	public Mask(int w, int h) {
		mask = new Result[w][h];
	}

	public void setResult(int x, int y, Result result) {
		if (x >= 0 && y >= 0 && x < mask.length && y < mask[0].length) {
			mask[x][y] = result;
		}
	}

	public Result getResult(int x, int y) {
		if (x < mask.length && y < mask[0].length) {
			return mask[x][y];
		} else {
			return Result.NULL;
		}
	}

	public int getWidth() {
		return mask.length;
	}

	public int getHeight() {
		return mask[0].length;
	}

}
