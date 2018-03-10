package com.adalbero.app.fractal.data;

import com.adalbero.app.fractal.ProgressListener;
import com.adalbero.app.fractal.functions.Fractal;

public class FractalPlotter implements Runnable {
	private Fractal fractal;
	private ComplexPlane complexPlane;
	private ProgressListener progress;
	private boolean abort = false;

	public FractalPlotter(Fractal fractal, ComplexPlane complexPlane, ProgressListener progress) {
		this.fractal = fractal;
		this.complexPlane = complexPlane;
		this.progress = progress;
	}

	@Override
	public void run() {
		int w = complexPlane.getCanvasWidth();
		int h = complexPlane.getCanvasHeight();

		if (w * h == 0)
			return;

		Mask mask = new Mask(w, h);
		int n = w * h;

		long t0 = System.currentTimeMillis();

		fractal.initResult();
		
		int cont = 0;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (abort) {
					return;
				}

				long time = System.currentTimeMillis() - t0;

				setResult(mask, x, y);

				if (progress != null && informProgress(cont, n, time)) {
					int p = cont * 100 / n + 1;
					progress.setProgress(mask, p, time);
				}
				cont++;
			}
		}

		if (progress != null) {
			long time = System.currentTimeMillis() - t0;
			progress.setProgress(mask, 100, time);
		}
	}

	protected void setResult(Mask mask, int x, int y) {
		int w = complexPlane.getCanvasWidth();
		x = w - x - 1;

		Complex point = complexPlane.getPoint(x, y);
		Result result = fractal.getResult(point);

		mask.setResult(x, y, result);
	}

	protected boolean informProgress(int cont, int n, long time) {
		return (cont % (n / 10) == 0);
		// return (time % (1000 / 4) == 0);

	}

	public void stop() {
		abort = true;
	}

}
