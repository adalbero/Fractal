package com.adalbero.app.fractal.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import com.adalbero.app.fractal.FractalApp;
import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.Result;
import com.adalbero.app.fractal.data.Util;
import com.adalbero.app.fractal.functions.Fractal;

public class IterationDialog extends Dialog {

	private static final long serialVersionUID = 1L;

	private FractalApp app;
	private GraphPanel graphPanel;

	private StatusBar statusBar;
	private Complex point;
	double tolerance;

	public IterationDialog(FractalApp app) {
		super(app.getMainFrame());
		this.setTitle("Iterations");

		this.app = app;

		addListeners(app);

		app.setIterationDialog(this);

		setLayout(new BorderLayout());
		graphPanel = new GraphPanel(this);

		statusBar = new StatusBar();
		statusBar.setPreferredSize(new Dimension(20, 20));

		this.add(graphPanel, BorderLayout.CENTER);
		this.add(statusBar, BorderLayout.PAGE_END);

		this.setSize(600, 300);

		this.setModal(false);
		this.setResizable(true);

		this.setLocationRelativeTo(null);
	}

	public void addListeners(FractalApp app) {
		Dialog me = this;

		me.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {
				app.setIterationDialog(null);
				me.dispose();
			}
		});
	}

	public void update(Complex point) {
		this.point = point;

		setMessage(-1, 0);

		Fractal f = app.getFractal();
		f.initResult();
		Result result = f.getResult(point);
		int n = result.iteraction;
		if (n < 0)
			n = 100;

		List<Complex> iterations = f.getIterations(point, n);
		this.tolerance = f.getTolerance();
		List<Complex> roots = f.getRoots();

		graphPanel.setGraph(iterations, tolerance, roots);
		graphPanel.repaint();

	}

	public void setMessage(int it, double value) {
		String msg = "(" + point + ") Tolerance: " + Util.format(tolerance);

		if (it >= 0) {
			msg += (" {it: " + it + " value: " + Util.format(value) + "}");
		}

		statusBar.setText(msg);
	}
}
