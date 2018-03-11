package com.adalbero.app.fractal.controller;

import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Mask;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Progress;
import com.adalbero.app.fractal.model.Result;

public class FractalPlotter implements Runnable {
	private Fractal fractal;
	private Plane plane;
	private ProgressListener progressListener;
	private boolean abort = false;

	public FractalPlotter(Fractal fractal, Plane plane, ProgressListener progressListener) {
		this.fractal = fractal;
		this.plane = plane;
		this.progressListener = progressListener;
	}

	@Override
	public void run() {
		if (plane == null)
			return;

		int w = plane.getCanvasWidth();
		int h = plane.getCanvasHeight();

		if (w * h == 0)
			return;

		Mask mask = new Mask(w, h);
		int n = w * h;

		Progress progress = new Progress(n);

		fractal.initResult();

		int cont = 0;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (abort) {
					return;
				}

				setResult(mask, x, y);
				
				progress.setProgress(cont);
				if (progressListener != null && progress.mod(10)) {
					progressListener.onProgress(progress, mask);
				}
				
				cont++;
			}
		}

		progress.done();
		if (progressListener != null) {
			progressListener.onProgress(progress, mask);
		}
	}

	protected void setResult(Mask mask, int x, int y) {
		int w = plane.getCanvasWidth();
		x = w - x - 1;

		Coordinate point = plane.getPoint(x, y);
		Result result = fractal.getResult(point);

		mask.setResult(x, y, result);
	}

	public void stop() {
		abort = true;
	}

}
