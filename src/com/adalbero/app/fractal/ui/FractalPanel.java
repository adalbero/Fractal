package com.adalbero.app.fractal.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.adalbero.app.fractal.FractalApp;
import com.adalbero.app.fractal.PaletteChangeListener;
import com.adalbero.app.fractal.ProgressListener;
import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.ComplexPlane;
import com.adalbero.app.fractal.data.FractalPlotter;
import com.adalbero.app.fractal.data.Mask;
import com.adalbero.app.fractal.data.Palette;
import com.adalbero.app.fractal.data.Result;
import com.adalbero.app.fractal.functions.Fractal;

public class FractalPanel extends Panel implements ProgressListener {
	private static final long serialVersionUID = 1L;

	private FractalApp app;
	private PaletteChangeListener paletteListener;

	private ComplexPlane complexPlane;
	private Palette palette;
	private Fractal fractal;

	private Image image;
	private Mask mask;

	private FractalPlotter fractalPlotter;

	private Point dragSource;
	private Point imageDragging;
	private int target = 0;

	public FractalPanel(Fractal f, FractalApp app, PaletteChangeListener listener) {
		this.app = app;
		this.paletteListener = listener;

		this.palette = new Palette(Palette.Name.RAINBOW);

		addListeners();

		setFractal(f);
	}

	public void setFractal(Fractal f) {
		this.fractal = f;
		this.complexPlane = f.getPreferedComplexPlane();

		changePalette(f.getPreferedPalette());
		setCanvasSize(this.getSize());
	}

	public void setCanvasSize(Dimension size) {
		complexPlane.setCanvasSize(size.width, size.height);

		repaintFractal();
	}

	private void addListeners() {
		FractalPanel me = this;

		me.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				setCanvasSize(me.getSize());
			}
		});

		me.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Complex point = complexPlane.getPoint(e.getX(), e.getY());

				if (e.isControlDown() || e.getClickCount() > 1) {

					if (e.isMetaDown()) {
						complexPlane.moveTo(point);
						complexPlane.zoom(2);
					} else {
						complexPlane.moveTo(point);
						complexPlane.zoom(0.5);
					}

					me.repaintFractal();
				}

				updateDialog(point);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				dragSource = e.getPoint();
				imageDragging = new Point();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (dragSource.x != e.getX() || dragSource.y != e.getY()) {
					Complex sourcePoint = complexPlane.getPoint(dragSource.x, dragSource.y);
					Complex eventPoint = complexPlane.getPoint(e.getX(), e.getY());
					Complex delta = sourcePoint.minus(eventPoint);

					complexPlane.moveBy(delta);
					updateDialog(complexPlane.getCenter());

					me.repaintFractal();
				}

				imageDragging = null;
			}

		});

		me.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				imageDragging.x = e.getX() - dragSource.x;
				imageDragging.y = e.getY() - dragSource.y;

				me.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if (app != null) {
					app.setPointMessage(e.getX(), e.getY());

					if (e.isShiftDown()) {
						ComplexPlane complexPlane = me.getComplexPlane();
						Complex point = complexPlane.getPoint(e.getX(), e.getY());
						updateDialog(point);
					}
				}
			}
		});

		me.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '+') {
					complexPlane.zoom(0.5);
					me.repaintFractal();
				} else if (e.getKeyChar() == '-') {
					complexPlane.zoom(2);
					me.repaintFractal();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					complexPlane.reset();
					onPaletteChanged();
					me.repaintFractal();
				} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
					me.getPalette().next();
					onPaletteChanged();
					me.repaintImage();
				} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
					me.getPalette().previous();
					onPaletteChanged();
					me.repaintImage();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					me.getPalette().scrollUp();
					onPaletteChanged();
					me.repaintImage();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					me.getPalette().scrollDown();
					onPaletteChanged();
					me.repaintImage();
				} else if (e.getKeyCode() == KeyEvent.VK_F2) {
					target = (target + 1) % 4;
					me.repaint();
				} else if (e.getKeyCode() == KeyEvent.VK_F5) {
					me.repaintFractal();
				}

			}
		});

	}

	public void updateDialog(Complex point) {
		if (app != null) {
			app.updateDialog(point);
		}
	}
	
	public void changePalette(Palette.Name name) {
		palette.draw(name);
		onPaletteChanged();
	}

	private void onPaletteChanged() {
		if (paletteListener != null) {
			paletteListener.onPaletteChanged(palette);
		}
	}

	public Fractal getFractal() {
		return fractal;
	}

	public ComplexPlane getComplexPlane() {
		return complexPlane;
	}

	public void setComplexPlaneCoordinates(Complex center, double radius) {
		complexPlane.setCoordinates(center, radius);

		repaintFractal();
	}

	public Palette getPalette() {
		return palette;
	}

	public Result getResultAt(int x, int y) {
		if (mask == null) {
			return Result.NULL;
		}

		return mask.getResult(x, y);
	}

	public void reset() {
		fractal.reset();

		setFractal(fractal);
	}

	@Override
	public void update(Graphics g) {
		if (isShowing()) {
			paint(g);
		}
	}

	@Override
	public void paint(Graphics g) {
		if (imageDragging != null) {
			moveImage(g);
			return;
		}

		if (image != null) {
			g.drawImage(image, 0, 0, this);
		}

		if (target > 0) {
			drawTarget(g, target);
		}

	}

	private void drawTarget(Graphics g, int color) {
		switch (color) {
		case 1:
			g.setColor(Color.GRAY);
			break;
		case 2:
			g.setColor(Color.WHITE);
			break;
		case 3:
			g.setColor(Color.RED);
			break;
		default:
			return;
		}

		int w = this.getWidth();
		int h = this.getHeight();

		g.drawLine(0, h / 2, w, h / 2);
		g.drawLine(w / 2, 0, w / 2, h);
	}

	private void moveImage(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();
		int x = imageDragging.x;
		int y = imageDragging.y;

		g.drawImage(image, imageDragging.x, imageDragging.y, this);
		g.clearRect(0, 0, w, y);
		g.clearRect(0, h + y, w, -y);
		g.clearRect(0, 0, x, h);
		g.clearRect(w + x, 0, -x, h);

		drawTarget(g, 1);
	}

	public void repaintFractal() {
		image = null;
		updateFractal();
	}

	public void repaintImage() {
		updateImage();

		this.repaint();
	}

	public void updateFractal() {
		if (fractalPlotter != null) {
			fractalPlotter.stop();
		}

		fractalPlotter = new FractalPlotter(fractal, complexPlane, this);
		new Thread(fractalPlotter).start();
	}

	public void updateImage() {
		if (mask != null) {
			image = getImage(mask, palette, fractal.getNumRoots());
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

	@Override
	public void setProgress(Mask mask, int p, long time) {

		if (app != null) {
			app.setProgress(mask, p, time);
		}

		if (p == 100) {
			this.mask = mask;
			repaintImage();
		}
	}

}
