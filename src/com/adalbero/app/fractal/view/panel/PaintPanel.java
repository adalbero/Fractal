package com.adalbero.app.fractal.view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.controller.FractalPlotter;
import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.controller.ProgressListener;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Mask;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Progress;
import com.adalbero.app.fractal.model.Result;

public class PaintPanel extends JPanel implements NotificationListener, ProgressListener {

	private static final long serialVersionUID = 1L;

	private FractalController controller;
	private Image image;

	private FractalPlotter fractalPlotter;
	private Mask mask;

	private Point dragDelta;
	private Point dragSource;

	public PaintPanel() {
		controller = FractalController.getInstance();

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

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (dragDelta == null && e.isShiftDown()) {
					// noop
				} else {
					if (dragDelta != null) {
						if (dragSource.x != e.getX() || dragSource.y != e.getY()) {
							Plane plane = controller.getPlane();

							Coordinate sourcePoint = plane.getPoint(dragSource.x, dragSource.y);
							Coordinate eventPoint = plane.getPoint(e.getX(), e.getY());
							Coordinate delta = sourcePoint.minus(eventPoint);

							plane.moveBy(delta);
							controller.broadcast(this, NotificationEvent.PLANE_CHANGED, null);
						}

						dragDelta = null;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isShiftDown()) {
					setTarget(e.getPoint());
					repaint();
				} else {
					dragSource = e.getPoint();
					dragDelta = new Point();
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.isShiftDown()) {
					setTarget(e.getPoint());
					repaint();
				} else if (e.isControlDown() || e.getClickCount() > 1) {
					Plane plane = controller.getPlane();
					Coordinate point = plane.getPoint(e.getX(), e.getY());

					if (e.isMetaDown()) {
						plane.setTarget(point);
//						plane.moveTo(point);
						plane.zoom(2);
					} else {
						plane.setTarget(point);
//						plane.moveTo(point);
						plane.zoom(0.5);
					}

					setTarget(null);
					controller.broadcast(this, NotificationEvent.PLANE_CHANGED, null);
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

		});

		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (dragDelta == null && e.isShiftDown()) {
					setTarget(e.getPoint());
					repaint();
				} else {
					if (dragDelta != null) {
						dragDelta.x = e.getX() - dragSource.x;
						dragDelta.y = e.getY() - dragSource.y;

						repaint();
					}
				}
			}
		});
	}

	private void setTarget(Point point) {
		Plane plane = controller.getPlane();

		if (point == null) {
			plane.setTarget(null);
		} else {
			plane.setTarget(plane.getPoint(point.x, point.y));
		}

		controller.broadcast(this, NotificationEvent.PLANE_TARGET_CHANGED, null);
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
		if (dragDelta != null) {
			moveImage(g);
		} else {
			updateImage();
			g.drawImage(image, 0, 0, this);
		}

		drawAxis(g);
		drawTarget(g);
	}

	private void moveImage(Graphics g) {
		if (image == null)
			return;

		int w = this.getWidth();
		int h = this.getHeight();
		int x = dragDelta.x;
		int y = dragDelta.y;

		g.drawImage(image, dragDelta.x, dragDelta.y, this);
		g.clearRect(0, 0, w, y);
		g.clearRect(0, h + y, w, -y);
		g.clearRect(0, 0, x, h);
		g.clearRect(w + x, 0, -x, h);
	}

	private void drawAxis(Graphics g) {
		Point p = getCanvasPoint(new Coordinate(0, 0));

		g.setColor(Color.GRAY);
		int w = this.getWidth();
		int h = this.getHeight();

		// X axis
		g.drawLine(0, p.y, w, p.y);

		// Y axis
		g.drawLine(p.x, 0, p.x, h);
	}

	private void drawTarget(Graphics g) {
		Coordinate target = controller.getPlane().getTarget();
		Point p = getCanvasPoint(target);

		g.setColor(Color.GRAY);
		int r = 10;
		int d = 2 * r;

		g.drawOval(p.x - r, p.y - r, d, d);
		g.drawLine(p.x, p.y - r, p.x, p.y - r + d);
		g.drawLine(p.x - r, p.y, p.x - r + d, p.y);
	}

	public Point getCanvasPoint(Coordinate p) {
		Plane plane = controller.getPlane();

		int x = plane.getCanvasX(p.getX());
		int y = plane.getCanvasY(p.getY());

		if (dragDelta != null) {
			x += dragDelta.x;
			y += dragDelta.y;
		}

		return new Point(x, y);
	}

	public void clearFractal() {
		mask = null;
		image = null;
	}
	
	public void repaintFractal() {
		updateFractal();
	}

	public void updateFractal() {
		if (fractalPlotter != null) {
			fractalPlotter.stop();
		}

		fractalPlotter = new FractalPlotter(controller.getFractal(), controller.getPlane(), this);

		new Thread(fractalPlotter).start();
		// fractalPlotter.run();
	}

	@Override
	public void onProgress(Progress progress, Object param) {
		
		controller.broadcast(this, NotificationEvent.PROGRESS_CHANGED, progress);
		
		if (progress.isDone()) {
			this.mask = (Mask) param;
			repaintImage();
		}
	}

	public void repaintImage() {
		updateImage();

		this.repaint();
	}

	public void updateImage() {
		if (mask != null) {
			image = getImage(mask, controller.getPalette(), controller.getFractal().getNumRoots());
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

	private void updateCanvasSize() {
		Plane plane = controller.getPlane();
		if (plane != null) {
			plane.setCanvasSize(this.getWidth(), this.getHeight());
		}
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (source == this)
			return;

		switch (event) {
		case FRACTAL_CHANGED:
			clearFractal();
		case PLANE_CHANGED:
		case FRACTAL_PARAM_CHANGED:
			updateCanvasSize();
			repaintFractal();
			break;
		case PLANE_TARGET_CHANGED:
			repaint();
			break;
		case PALETTE_CHANGED:
			updateCanvasSize();
			repaintImage();
			break;
		default:
			break;
		}
	}

}
