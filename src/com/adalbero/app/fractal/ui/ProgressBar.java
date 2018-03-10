package com.adalbero.app.fractal.ui;

import java.awt.Label;

import com.adalbero.app.fractal.ProgressListener;
import com.adalbero.app.fractal.data.Mask;

public class ProgressBar extends Label implements ProgressListener {

	private static final long serialVersionUID = 1L;

	public ProgressBar() {
		super("Progress: 100%");
	}

	@Override
	public void setProgress(Mask mask, int p, long time) {
		String msg;
		msg = String.format("Progress: %d%%", p);
		
		if (time > 0) {
			msg += String.format(" Time: %.2f secs", time/ 1000f);
		}

		this.setText(msg);
	}
}
