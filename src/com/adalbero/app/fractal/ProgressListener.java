package com.adalbero.app.fractal;

import com.adalbero.app.fractal.data.Mask;

public interface ProgressListener {
	public void setProgress(Mask mask, int p, long time);
}
