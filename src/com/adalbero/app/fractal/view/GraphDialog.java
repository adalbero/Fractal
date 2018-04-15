package com.adalbero.app.fractal.view;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Result;
import com.adalbero.app.fractal.model.Serie;
import com.adalbero.app.fractal.view.canvas.GraphCanvas;

public class GraphDialog extends DetailDialog {

	private static final long serialVersionUID = 1L;

	private GraphCanvas graphPanel;

	public GraphDialog(Frame frame, Fractal f) {
		super(frame, f);

		this.setTitle("Iterations");
	}

	@Override
	public void update(Complex target) {
		Fractal f = getFractal();

		List<Complex> roots = f.getRoots();

		Result result = f.getResult(target);
		int maxPoints = result.iteraction;

		if (result.iteraction < 0)
			maxPoints = 100;

		List<Coordinate> points = f.getIterations(target, maxPoints);
		double tolerance = f.getTolerance();

		List<Serie> series = new ArrayList<>();

		if (roots == null) {
			Serie serie = new Serie();
			for (Coordinate p : points) {
				serie.add(p.mod());
			}
			series.add(serie);

		} else {
			tolerance = 0;
			for (Complex root : roots) {
				Serie serie = new Serie();
				for (Coordinate p : points) {
					Complex z = new Complex(p);

					serie.add(z.minus(root).mod());
				}
				series.add(serie);
			}
		}

		graphPanel.setGraph(points, series, tolerance);
	}

	@Override
	public JPanel getMainPanel() {
		graphPanel = new GraphCanvas();
		return graphPanel;
	}

}
