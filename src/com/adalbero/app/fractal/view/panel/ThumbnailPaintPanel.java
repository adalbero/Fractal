package com.adalbero.app.fractal.view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import com.adalbero.app.fractal.controller.FractalPlotter;
import com.adalbero.app.fractal.controller.ProgressListener;
import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Mask;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Progress;
import com.adalbero.app.fractal.model.Result;

public class ThumbnailPaintPanel extends JPanel implements ProgressListener {

	private static final long serialVersionUID = 1L;

	private Fractal fractal;
	private Plane plane;
	private Palette palette;

	private Image image;
	private boolean drawTarget = false;

	private FractalPlotter fractalPlotter;
	private Mask mask;

	public ThumbnailPaintPanel(Fractal f) {
		this.fractal = f;
		this.plane = f.getPreferedPlane();
		this.palette = new Palette(f.getPreferedPalette());

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				repaintFractal();
			}

		});
	}

	public Fractal getFractal() {
		return this.fractal;
	}

	public void setTarget(Complex target) {
		plane.setTarget(target);
		drawTarget = true;

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
		Image offlineImage = createImage(this.getWidth(), this.getHeight());
		paintOffscreen(offlineImage.getGraphics());
		g.drawImage(offlineImage, 0, 0, this);
	}

	private void paintOffscreen(Graphics g) {
		updateImage();
		g.drawImage(image, 0, 0, this);

		drawTarget(g);
	}

	public void updateImage() {
		if (mask != null) {
			image = getImage(mask, this.palette, this.fractal.getNumRoots());
		}
	}

	private Image getImage(Mask m, Palette p, int roots) {
		int w = m.getWidth();
		int h = m.getHeight();

		Image img = createImage(w, h);

		Graphics gr = img.getGraphics();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Result result = m.getResult(i, j);
				Color c = p.getColor(result, roots);

				gr.setColor(c);
				gr.drawLine(i, j, i, j);
			}
		}

		return img;
	}

	private void drawTarget(Graphics g) {
		if (drawTarget) {
			Coordinate target = plane.getTarget();
			Point p = getCanvasPoint(target);

			g.setColor(Color.GRAY);
			int r = 10;
			int d = 2 * r;

			g.drawOval(p.x - r, p.y - r, d, d);
			g.drawLine(p.x, p.y - r, p.x, p.y - r + d);
			g.drawLine(p.x - r, p.y, p.x - r + d, p.y);
		}
	}

	public Point getCanvasPoint(Coordinate p) {
		int x = plane.getCanvasX(p.getX());
		int y = plane.getCanvasY(p.getY());

		return new Point(x, y);
	}

	public void repaintFractal() {
		updateCanvasSize();
		updateFractal();
	}

	public void updateFractal() {
		if (fractalPlotter != null) {
			fractalPlotter.stop();
		}

		fractalPlotter = new FractalPlotter(this.fractal, this.plane, this);

		new Thread(fractalPlotter).start();
	}

	@Override
	public void onProgress(Progress progress, Object param) {

		if (progress.isDone()) {
			this.mask = (Mask) param;
			repaintImage();
		}
	}

	public void repaintImage() {
		updateImage();

		this.repaint();
	}

	private void updateCanvasSize() {
		plane.setCanvasSize(this.getWidth(), this.getHeight());
	}

}
