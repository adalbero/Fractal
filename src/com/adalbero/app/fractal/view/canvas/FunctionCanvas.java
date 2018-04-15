package com.adalbero.app.fractal.view.canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.adalbero.app.fractal.functions.Newton;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Result;

public class FunctionCanvas extends JPanel {
	private static final long serialVersionUID = 1L;

	private Image image;

	private Newton function;
	private Plane plane;
	private Palette palette;

	private Coordinate target;

	private List<Double> serie;

	private float[] dash = new float[] { 4.0f, 4.0f };
	private BasicStroke lineStroke = new BasicStroke(2.0f);
	private BasicStroke dottedStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f, dash, 0);

	public FunctionCanvas(Newton function) {
		this.function = function;
		this.plane = function.getPreferedPlane();
		this.palette = new Palette(Palette.Name.RAINBOW_MOD2);

		addListeners();
	}

	private void addListeners() {
		FunctionCanvas me = this;

		me.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				calcSerie();
			}
		});

		me.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.isMetaDown()) {
					plane.zoom(2.0);
				} else {
					plane.zoom(0.5);
				}
				calcSerie();
			}
		});

		me.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (e.isShiftDown()) {
					setTargetX(e.getX());
				}
			}
		});
	}

	public void setTarget(Coordinate target) {
		boolean needRecalc = (this.target == null || this.target.getY() != target.getY());

		this.target = target;

		this.target.setY(0);

		if (needRecalc) {
			calcSerie();
		} else {
			this.repaint();
		}
	}

	private void setTargetX(int i) {
		setTarget(new Coordinate(plane.getX(i), target.getY()));
	}

	private void calcSerie() {
		plane.setCanvasSize(this.getWidth(), this.getHeight());

		serie = new ArrayList<>();

		for (int i = 0; i < this.getWidth(); i++) {
			double x = plane.getX(i);
			Complex z = new Complex(x, target.getY());
			Complex y = function.f(z);
			serie.add(y.getRe());
		}

		drawImage();

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
		if (this.getWidth() * this.getHeight() > 0) {
			Image offlineImage = createImage(this.getWidth(), this.getHeight());
			Graphics2D g2 = (Graphics2D) offlineImage.getGraphics();
			paintOffscreen(g2);
			g.drawImage(offlineImage, 0, 0, this);
		}
	}

	private void paintOffscreen(Graphics2D g2) {
		if (image != null) {
			g2.drawImage(image, 0, 0, this);
		}

		drawTarget(g2);
	}

	public void drawImage() {
		int w = this.getWidth();
		int h = this.getHeight();

		if (w * h == 0)
			return;

		image = createImage(w, h);

		Graphics gr = image.getGraphics();
		Graphics2D g2 = (Graphics2D) gr;
		drawImage(g2);
	}

	public void drawImage(Graphics2D g2) {
		int w = this.getWidth();
		int h = this.getHeight();
		int cx, cy;

		drawConvergence(g2);
		drawSerie(g2);

		cx = plane.getCanvasX(0);
		cy = plane.getCanvasY(0);

		// Axis Y
		g2.setColor(Color.BLACK);
		g2.drawLine(cx, 0, cx, h);

		// Axis X
		g2.drawLine(0, cy, w, cy);

	}

	private void drawSerie(Graphics2D g2) {
		int i0, j0, i1, j1;

		Color c = Color.MAGENTA;
		g2.setColor(c);
		g2.setStroke(lineStroke);

		double v1 = serie.get(0);
		for (int i = 1; i < serie.size(); i++) {
			double v2 = serie.get(i);
			i0 = i - 1;
			i1 = i;
			j0 = plane.getCanvasY(v1);
			j1 = plane.getCanvasY(v2);

			g2.drawLine(i0, j0, i1, j1);

			v1 = v2;
		}
	}

	private void drawConvergence(Graphics2D g2) {
		int j;
		int r = 10;
		int roots = function.getNumRoots();

		j = plane.getCanvasY(0);

		for (int i = 1; i < serie.size(); i++) {
			Complex z = new Complex(plane.getX(i), target.getY());
			Result result = function.getResult(z);

			r = (result.iteraction - 1) * 10;

			Color c = palette.getColor(result, roots);
			g2.setColor(c);

			g2.drawLine(i, j - r, i, j);
		}
	}

	private void drawTarget(Graphics2D g2) {
		if (serie == null)
			return;

		int r = 5;

		int i = plane.getCanvasX(target.getX());
		int j = plane.getCanvasY(0);

		if (i < 0)
			i = 0;

		Complex z = new Complex(plane.getPoint(i, j));
		Complex zn = z.minus(function.f(z).div(function.df(z)));

		int ii = plane.getCanvasX(zn.getRe());
		int jj = plane.getCanvasY(serie.get(i));

		// z0
		g2.setColor(Color.BLUE);
		g2.drawOval(i - r, j - r, 2 * r, 2 * r);

		// f(z0)
		g2.setColor(Color.RED);
		g2.drawOval(i - r, jj - r, 2 * r, 2 * r);

		// z1
		g2.setColor(Color.BLACK);
		g2.drawOval(ii - r, j - r, 2 * r, 2 * r);

		g2.setStroke(dottedStroke);
		g2.setColor(Color.BLUE);
		g2.drawLine(i, j, i, jj);
		g2.setStroke(lineStroke);
		g2.setColor(Color.RED);
		g2.drawLine(i, jj, ii, j);

	}
}