package com.adalbero.app.fractal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.ComplexPlane;
import com.adalbero.app.fractal.data.Mask;
import com.adalbero.app.fractal.data.Palette;
import com.adalbero.app.fractal.data.Result;
import com.adalbero.app.fractal.functions.Circle;
import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.functions.JuliaSet;
import com.adalbero.app.fractal.functions.MandelbrotOptimized;
import com.adalbero.app.fractal.functions.NewtonSin;
import com.adalbero.app.fractal.functions.NewtonTest;
import com.adalbero.app.fractal.functions.NewtonZ3;
import com.adalbero.app.fractal.functions.NewtonZ3b;
import com.adalbero.app.fractal.functions.NewtonZ5;
import com.adalbero.app.fractal.functions.NewtonZ5b;
import com.adalbero.app.fractal.functions.NewtonZ6;
import com.adalbero.app.fractal.functions.NewtonZ8;
import com.adalbero.app.fractal.ui.FractalPanel;
import com.adalbero.app.fractal.ui.IterationDialog;
import com.adalbero.app.fractal.ui.JuliaSetDialog;
import com.adalbero.app.fractal.ui.PalettePanel;
import com.adalbero.app.fractal.ui.StatusBar;
import com.adalbero.app.fractal.ui.ToolBar;

public class FractalApp implements ProgressListener {
	private Frame mainFrame;
	private ToolBar toolBar;
	private FractalPanel fractalPanel;
	private PalettePanel palettePanel;
	private StatusBar statusBar;
	private JuliaSetDialog juliaSetDialog;
	private IterationDialog iterationDialog;

	Fractal fractals[] = { new MandelbrotOptimized(), new JuliaSet(), new NewtonTest(), new NewtonZ3(), new NewtonZ3b(),
			new NewtonZ5(), new NewtonZ5b(), new NewtonZ6(), new NewtonSin(), new NewtonZ8(), new Circle() };

	public static void main(String[] args) {
		new FractalApp().run();
	}

	public void run() {
		initPanel();
	}

	public Fractal[] getFractals() {
		return fractals;
	}

	private void initPanel() {
		Fractal f = fractals[0];

		mainFrame = new Frame("Fractals");
		mainFrame.setSize(1000, 800);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		toolBar = new ToolBar(this);

		palettePanel = new PalettePanel(this);
		palettePanel.setPreferredSize(new Dimension(20, 20));

		fractalPanel = new FractalPanel(f, this, palettePanel);

		statusBar = new StatusBar();
		statusBar.setPreferredSize(new Dimension(20, 20));

		mainFrame.add(toolBar, BorderLayout.PAGE_START);
		mainFrame.add(fractalPanel, BorderLayout.CENTER);
		mainFrame.add(palettePanel, BorderLayout.LINE_START);
		mainFrame.add(statusBar, BorderLayout.PAGE_END);

		mainFrame.setVisible(true);
	}

	public Frame getMainFrame() {
		return this.mainFrame;
	}

	public Fractal getFractal() {
		return fractalPanel.getFractal();
	}

	public Palette getPalette() {
		return fractalPanel.getPalette();
	}

	public ComplexPlane getComplexPlane() {
		return fractalPanel.getComplexPlane();
	}

	public void setComplexPlaneCoordinates(Complex center, double radius) {
		fractalPanel.setComplexPlaneCoordinates(center, radius);
		updateDialog(center);
	}

	public void setProgress(Mask mask, int p, long time) {
		toolBar.setProgress(mask, p, time);
	}

	public void setMessage(String msg) {
		statusBar.setMessage(msg);
	}

	public void setIterationDialog(IterationDialog dialog) {
		this.iterationDialog = dialog;
	}

	public void setJuliaSetDialog(JuliaSetDialog dialog) {
		this.juliaSetDialog = dialog;
	}

	public void updateDialog(Complex point) {
		if (juliaSetDialog != null) {
			juliaSetDialog.update(point);
		}
		if (iterationDialog != null) {
			iterationDialog.update(point);
		}
	}

	public void setPointMessage(int x, int y) {
		Fractal fractal = fractalPanel.getFractal();
		ComplexPlane complexPlane = fractalPanel.getComplexPlane();
		Result result = fractalPanel.getResultAt(x, y);
		Complex point = complexPlane.getPoint(x, y);

		String msg = complexPlane.toString();
		msg += " | " + fractal.toString();
		msg += " | " + point.format(true);
		msg += " | " + result;

		setMessage(msg);
	}

	public void reset() {
		fractalPanel.reset();
	}

	public void repaintFractal() {
		fractalPanel.repaintFractal();
	}

	public void repaintImage() {
		fractalPanel.repaintImage();
	}

	public void selectFractal(int idx) {
		Fractal f = fractals[idx];
		fractalPanel.setFractal(f);
	}

}
