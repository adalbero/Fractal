package com.adalbero.app.fractal.view;

import java.awt.Frame;

import javax.swing.JPanel;

import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.view.panel.ThumbnailPaintPanel;

public class MapDialog extends DetailDialog {

	private static final long serialVersionUID = 1L;

	private ThumbnailPaintPanel fractalPanel;

	public MapDialog(Frame frame, Fractal fractal) {
		super(frame, fractal);

		this.setTitle("Map of " + fractal.toString());
	}

	public void update(Complex c) {
		fractalPanel.setTarget(c);
	}

	@Override
	public JPanel getMainPanel() {
		fractalPanel = new ThumbnailPaintPanel(getFractal());
		return fractalPanel;
	}
}
