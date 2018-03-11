package com.adalbero.app.fractal.view;

import java.awt.Frame;

import javax.swing.JPanel;

import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.functions.JuliaSet;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.view.panel.ThumbnailPaintPanel;

public class JuliaSetDialog extends DetailDialog {

	private static final long serialVersionUID = 1L;

	private ThumbnailPaintPanel fractalPanel;

	public JuliaSetDialog(Frame frame, Fractal f) {
		super(frame, f);

		this.setTitle("Julia Set");
	}

	@Override
	public void update(Complex c) {
		fractalPanel.getFractal().getParams().setParam(JuliaSet.C, c);
		fractalPanel.repaintFractal();
	}

	@Override
	public JPanel getMainPanel() {
		fractalPanel = new ThumbnailPaintPanel(getFractal());
		return fractalPanel;
	}
}
