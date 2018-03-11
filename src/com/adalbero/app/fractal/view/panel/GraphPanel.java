package com.adalbero.app.fractal.view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Util;

public class GraphPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Image image;

	private List<Coordinate> iterations;
	private double tolerance;
	private List<Complex> roots;

	private int w;
	private int h;
	private int m;
	private int n;
	private double dx;
	private double dy;
	private double maxValue;

	int focus = -1;

	public GraphPanel() {
		addListeners();
	}

	private void addListeners() {
		GraphPanel me = this;

		me.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				calcScale();
			}
		});

		me.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int it = getIter(e.getX());
				setFocus(it);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
			}

		});

	}

	private void setFocus(int it) {
		if (iterations != null) {
			focus = it;

			this.repaint();
		}
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

		if (roots == null && focus >= 0) {
			int R = 6;

			double value = getValue(focus);
			int x1 = getX(focus);
			int y1 = getY(value);

			g.setColor(Color.BLUE);
			g.drawOval(x1 - R / 2, y1 - R / 2, R, R);

			if (focus < iterations.size()) {
				String str = iterations.get(focus).toString();
				int strW = g.getFontMetrics().stringWidth(str);
				g.drawString(str, x1 - strW / 2, h - 10);

				str = Util.format(value);
				strW = g.getFontMetrics().stringWidth(str);
				g.drawString(str, x1 - strW / 2, y1 - 10);
			}
		}
	}

	public void setGraph(List<Coordinate> iterations, double tolerance, List<Complex> roots) {
		this.iterations = iterations;
		this.tolerance = tolerance;
		this.roots = roots;

		calcScale();

		this.repaint();
	}

	public void calcScale() {
		w = this.getWidth();
		h = this.getHeight();
		m = 30;

		if (iterations != null) {
			n = iterations.size();
			maxValue = getMaxValue();
			dx = (w - 2 * m) / (double) (n - 1);
			dy = (h - 2 * m) / maxValue;
		}

		focus = -1;
		drawImage();
	}

	public double getMaxValue() {
		if (roots == null) {
			return tolerance;
		}

		double max = 0;
		for (int i = 0; i < iterations.size(); i++) {
			double v = getValue(i);
			if (v > max)
				max = v;
		}

		return max * 2;
	}

	public void drawImage() {
		if (w * h == 0 || iterations == null)
			return;

		image = createImage(w, h);
		Graphics gr = image.getGraphics();

		String str;
		int strW;

		// Axis
		gr.setColor(Color.BLACK);
		gr.drawLine(0, h - m, w - m, h - m);
		gr.drawLine(m, 0, m, h);

		for (int i = 1; i < n; i++) {
			int x1 = getX(i);
			gr.setColor(Color.BLACK);
			gr.drawLine(x1, h - m, x1, h - m - 5);
		}

		int x = getX(n - 1);
		str = "" + (n - 1);
		strW = gr.getFontMetrics().stringWidth(str);
		gr.drawString(str, x - strW / 2, h - 10);

		int y = getY(maxValue);
		// Tolerance
		// if (roots == null) {
		// gr.setColor(Color.RED);
		// } else {
		gr.setColor(Color.MAGENTA);
		// }

		gr.drawLine(m - 2, y, w - m, y);
		str = Util.format(maxValue);
		strW = gr.getFontMetrics().stringWidth(str);
		gr.drawString(str, x - strW, y - 5);

		// Grid X

		if (roots != null) {
			for (int r = 0; r < roots.size(); r++) {
				drawSerie(gr, r);
			}
		} else {
			drawSerie(gr, -1);
		}
	}

	private void drawSerie(Graphics gr, int r) {
		int x0, x1, y0, y1;

		Color c;

		if (r < 0) {
			c = Color.RED;
		} else {
			c = Color.getHSBColor((float) (r + 1) / roots.size(), 1, 1);
		}

		double v1 = getRootDistance(0, r);
		for (int i = 1; i < n; i++) {
			double v2 = getRootDistance(i, r);
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
		return m + (int) (i * dx);
	}

	private int getY(double value) {
		return h - m - (int) (value * dy);
	}

	private int getIter(int x) {
		return (int) ((x - m) / dx + 0.5);
	}

	private double getValue(int it) {
		if (iterations != null && it >= 0 && it < iterations.size()) {
			return iterations.get(it).mod();
		} else {
			return 0;
		}
	}

	private double getRootDistance(int it, int r) {
		if (r < 0) {
			return getValue(it);
		} else if (iterations != null && it >= 0 && it < iterations.size()) {
			Complex p = (Complex) iterations.get(it);
			Complex root = roots.get(r);

			return p.minus(root).mod();
		} else {
			return 0;
		}
	}
}
