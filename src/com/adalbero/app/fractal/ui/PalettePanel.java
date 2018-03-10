package com.adalbero.app.fractal.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;

import com.adalbero.app.fractal.FractalApp;
import com.adalbero.app.fractal.PaletteChangeListener;
import com.adalbero.app.fractal.data.Palette;

public class PalettePanel extends Panel implements PaletteChangeListener {
	private static final long serialVersionUID = 1L;

	private Palette palette;

	public PalettePanel(FractalApp app) {
		this.setBackground(Color.LIGHT_GRAY);
	}

	public void onPaletteChanged(Palette p) {
		this.palette = p;
		this.repaint();
	}

	public Palette getPalette() {
		return palette;
	}

	public void paint(Graphics g) {
		if (palette != null) {
			Dimension size = this.getSize();
			int w = size.width;
			int h = size.height;
			double f = (double) palette.getLength() / h;

			for (int j = 0; j < h; j++) {
				int idx = (int) (f * j);
				Color c = palette.getColor(idx);
				float[] hsbvals = new float[3];
				Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsbvals);

				g.setColor(c);
				g.drawLine(2, j, w - 2, j);
			}
		}
	}

}
