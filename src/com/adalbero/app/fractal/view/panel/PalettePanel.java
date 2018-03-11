package com.adalbero.app.fractal.view.panel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Palette;

public class PalettePanel extends JPanel implements NotificationListener {
	private static final long serialVersionUID = 1L;

	private Palette palette;
	private boolean isHorizontal = true;

	public PalettePanel() {
		this.setBackground(Color.LIGHT_GRAY);
	}

	public void paint(Graphics g) {
		if (palette != null) {
			int w = this.getWidth();
			int h = this.getHeight();
			int size = (isHorizontal ? w : h);
			double f = (double) palette.getLength() / size;

			for (int j = 0; j < size; j++) {
				int idx = (int) (f * j);
				Color c = palette.getColor(idx);

				g.setColor(c);

				if (isHorizontal) {
					g.drawLine(j, 0, j, h);
				} else {
					g.drawLine(0, j, w, j);
				}
			}
		}
	}

	private void update() {
		palette = FractalController.getInstance().getPalette();

		this.repaint();
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (event == NotificationEvent.PALETTE_CHANGED) {
			update();
		}
	}

}
