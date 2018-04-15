package com.adalbero.app.fractal.view.canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JPanel;

import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Serie;
import com.adalbero.app.fractal.model.Util;

public class GraphCanvas extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int MARGIN = 30;

	private Image image;

	private List<Coordinate> points;
	private List<Serie> series;
	double threshold;

	private int w;
	private int h;
	private double dx;
	private double dy;
	private double maxValue;

	public GraphCanvas() {
		addListeners();
	}

	private void addListeners() {
		GraphCanvas me = this;

		me.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				calcScale();
			}
		});
	}

	public void setGraph(List<Coordinate> points, List<Serie> series, double threshold) {
		this.points = points;
		this.series = series;
		this.threshold = threshold;

		calcScale();

		this.repaint();
	}

	@Override
	public void update(Graphics g) {
		if (isShowing()) {
			paint(g);
		}
	}

	@Override
	public void paint(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, this);
		}
	}

	public void calcScale() {
		w = this.getWidth();
		h = this.getHeight();

		int n = points.size();
		maxValue = getMaxValue();
		dx = (w - 2 * MARGIN) / (double) (n - 1);
		dy = (h - 2 * MARGIN) / maxValue;

		drawImage();
	}

	public double getMaxValue() {
		if (threshold > 0) {
			return threshold;
		}

		double value = 0;

		for (Serie serie : series) {
			value = Math.max(value, serie.getMaxValue());
		}

		return value;
	}

	public void drawImage() {
		if (w * h == 0)
			return;

		image = createImage(w, h);
		Graphics gr = image.getGraphics();

		String str;
		int strW;
		int x, y;

		// Axis Y
		gr.setColor(Color.BLACK);
		gr.drawLine(0, h - MARGIN, w - MARGIN, h - MARGIN);

		// Threshold
		if (threshold > 0) {
			y = getY(maxValue);
			gr.setColor(Color.MAGENTA);
			gr.drawLine(MARGIN - 2, y, w - MARGIN, y);
			str = Util.format(maxValue);
			strW = gr.getFontMetrics().stringWidth(str);
			gr.drawString(str, 0, y - 5);
		}

		// Axis X
		gr.drawLine(MARGIN, 0, MARGIN, h);

		int n = points.size();
		for (int i = 1; i < n; i++) {
			x = getX(i);
			gr.setColor(Color.BLACK);
			gr.drawLine(x, h - MARGIN, x, h - MARGIN - 5);
		}

		// Last X
		x = getX(n - 1);
		str = "" + (n - 1);
		strW = gr.getFontMetrics().stringWidth(str);
		gr.drawString(str, x - strW / 2, h - 10);

		// Series
		for (int s = 0; s < series.size(); s++) {
			drawSerie(gr, s);
		}

	}

	private void drawSerie(Graphics gr, int s) {
		int x0, x1, y0, y1;

		Color c;

		c = Color.getHSBColor((float) (s) / series.size(), 1, 1);

		Serie serie = series.get(s);

		double v1 = serie.getValue(0);
		for (int i = 1; i < points.size(); i++) {
			double v2 = serie.getValue(i);
			x0 = getX(i - 1);
			x1 = getX(i);
			y0 = getY(v1);
			y1 = getY(v2);

			gr.setColor(c);

			gr.drawLine(x0, y0, x1, y1);

			gr.drawOval(x1 - 2, y1 - 2, 4, 4);

			v1 = v2;
		}

	}

	private int getX(int i) {
		return MARGIN + (int) (i * dx);
	}

	private int getY(double value) {
		return h - MARGIN - (int) (value * dy);
	}

}
