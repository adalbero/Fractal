package com.adalbero.app.fractal.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

import javax.swing.JPanel;

import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Result;
import com.adalbero.app.fractal.view.panel.GraphPanel;

public class GraphDialog extends DetailDialog {

	private static final long serialVersionUID = 1L;

	private GraphPanel graphPanel;

	public GraphDialog(Frame frame, Fractal f) {
		super(frame, f);

		this.setTitle("Iterations");

		setLayout(new BorderLayout());

		this.add(graphPanel, BorderLayout.CENTER);

		this.setSize(600, 300);

		this.setModal(false);
		this.setResizable(true);

		this.setLocationRelativeTo(null);
	}

	@Override
	public void update(Complex point) {
		Fractal f = getFractal();

		f.initResult();
		Result result = f.getResult(point);
		int n = result.iteraction;
		if (n < 0)
			n = 100;

		List<Coordinate> iterations = f.getIterations(point, n);
		double tolerance = f.getTolerance();
		List<Complex> roots = f.getRoots();

		graphPanel.setGraph(iterations, tolerance, roots);
		graphPanel.repaint();
	}

	@Override
	public JPanel getMainPanel() {
		graphPanel = new GraphPanel();
		return graphPanel;
	}

}
