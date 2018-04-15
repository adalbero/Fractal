package com.adalbero.app.fractal.view.canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.functions.IteratedFunction;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Transformation;

public class IteratedCanvas extends PaintCanvas {

	private static final long serialVersionUID = 1L;

	private Color GREEN = new Color(0, 128, 0);

	private FractalController controller;
	private Image image;
	private BasicStroke lineStroke = new BasicStroke(2.0f);

	private List<Coordinate> polygon;

	public IteratedCanvas() {
		controller = FractalController.getInstance();

		double h = 10;
		double r = h/2;
		polygon = new ArrayList<>();
		polygon.add(new Coordinate(-r, 0));
		polygon.add(new Coordinate(r, 0));
		polygon.add(new Coordinate(r, h));
		polygon.add(new Coordinate(-r, h));

		addListeners();
	}

	private void addListeners() {

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateCanvasSize();
				controller.broadcast(this, NotificationEvent.PLANE_CHANGED, null);
			}

		});

	}

	private void updateCanvasSize() {
		Plane plane = controller.getPlane();
		if (plane != null) {
			plane.setCanvasSize(this.getWidth(), this.getHeight());
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
		Image offlineImage = createImage(this.getWidth(), this.getHeight());
		Graphics2D gr2 = (Graphics2D) offlineImage.getGraphics();
		paintOffscreen(gr2);
		g.drawImage(offlineImage, 0, 0, this);
	}

	private void paintOffscreen(Graphics2D g2) {
		updateImage();

		if (image != null) {
			g2.drawImage(image, 0, 0, this);
		}

		drawAxis(g2);

		drawTransformations(g2);
	}

	private void drawAxis(Graphics2D g2) {
		Coordinate center = new Coordinate(0, 0);

		Point dot = getCanvasPoint(center);

		g2.setColor(Color.GRAY);
		int w = this.getWidth();
		int h = this.getHeight();

		// X axis
		g2.drawLine(0, dot.y, w, dot.y);

		// Y axis
		g2.drawLine(dot.x, 0, dot.x, h);
	}

	private void drawTransformations(Graphics2D g2) {
		IteratedFunction f = (IteratedFunction) controller.getFractal();
		int n = f.getTransformations().getSize();

		g2.setStroke(lineStroke);
		g2.setColor(Color.BLACK);
		drawPolygon(g2, polygon);

		for (int i = 0; i < n; i++) {
			Transformation t = f.getTransformations().getTransformation(i);
			List<Coordinate> poly = t.transform(polygon);

			g2.setColor(Color.getHSBColor((float) i / n, 1, 1));
			drawPolygon(g2, poly);

		}
	}

	private void drawPolygon(Graphics2D g2, List<Coordinate> poly) {
		int n = polygon.size();

		for (int i = 0; i < n; i++) {
			Point p1 = getCanvasPoint(poly.get(i));
			Point p2 = getCanvasPoint(poly.get((i + 1) % n));

			g2.drawLine(p1.x, p1.y, p2.x, p2.y);
		}

		int r = 5;
		Point p = getCanvasPoint(poly.get(0));
		g2.drawOval(p.x - r, p.y - r, 2 * r, 2 * r);
	}

	private void updateImage() {
		int w = this.getWidth();
		int h = this.getHeight();

		if (w * h == 0)
			return;

		image = createImage(w, h);

		Graphics2D g2 = (Graphics2D) image.getGraphics();

		updateImage(g2);
	}

	private void updateImage(Graphics2D g2) {
		IteratedFunction f = (IteratedFunction) controller.getFractal();

		int n = f.getMaxIterations();

		for (int i = 0; i < n; i++) {
			Coordinate p = f.getNextPoint();

			Point dot = getCanvasPoint(p);

			// g2.setStroke(STROKE);
			g2.setColor(GREEN);
			g2.drawLine(dot.x, dot.y, dot.x, dot.y);
		}
	}

	private Point getCanvasPoint(Coordinate p) {
		Plane plane = controller.getPlane();

		int x = plane.getCanvasX(p.getX());
		int y = plane.getCanvasY(p.getY());

		return new Point(x, y);
	}

	private void repaintFractal() {
		updateImage();
		this.repaint();
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (source == this)
			return;

		switch (event) {
		case FRACTAL_CHANGED:
		case PLANE_CHANGED:
		case FRACTAL_PARAM_CHANGED:
		case PLANE_TARGET_CHANGED:
			repaintFractal();
			break;
		default:
			break;
		}
	}

}
