package com.adalbero.app.fractal.controller;

import com.adalbero.app.fractal.model.Progress;

public interface ProgressListener {
	public void onProgress(Progress progress, Object param);
}
