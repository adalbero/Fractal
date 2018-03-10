package com.adalbero.app.fractal.ui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.adalbero.app.fractal.FractalApp;
import com.adalbero.app.fractal.ProgressListener;
import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.data.ComplexPlane;
import com.adalbero.app.fractal.data.Mask;
import com.adalbero.app.fractal.data.Util;
import com.adalbero.app.fractal.functions.Fractal;

public class ToolBar extends Panel implements ProgressListener {

	private static final long serialVersionUID = 1L;

	private ProgressBar progressBar;

	ParamsDialog paramsDialog;

	public ToolBar(FractalApp app) {

		Choice selectFractal = new Choice();
		for (Fractal f : app.getFractals()) {
			selectFractal.add(f.getName());
		}
		selectFractal.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				int idx = selectFractal.getSelectedIndex();
				app.selectFractal(idx);
			}
		});

		Button btnParams = new Button("Params");
		btnParams.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				if (paramsDialog == null) {
					paramsDialog = new ParamsDialog(app);
				}

				paramsDialog.showModalLess(null);
			}
		});

		Button btnReset = new Button("Reset");
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				app.reset();
			}
		});

		Button btnPlane = new Button("Plane");
		btnPlane.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				planeDialog(app);
			}
		});

		Button btnJuliaSetDialog = new Button("JuliaSet");
		btnJuliaSetDialog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				Dialog d = new JuliaSetDialog(app);
				d.setVisible(true);
			}
		});

		Button btnIterationDialog = new Button("Iteration");
		btnIterationDialog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				Dialog d = new IterationDialog(app);
				d.setVisible(true);
			}
		});

		progressBar = new ProgressBar();
		progressBar.setPreferredSize(new Dimension(200, 30));
		progressBar.setProgress(null, 0, 0);

		this.setPreferredSize(new Dimension(30, 40));
		this.setBackground(Color.LIGHT_GRAY);
		this.add(selectFractal, null);
		this.add(btnParams, null);
		this.add(btnReset, null);
		this.add(btnPlane, null);
		this.add(btnJuliaSetDialog, null);
		this.add(btnIterationDialog, null);
		this.add(progressBar, null);
	}

	@Override
	public void setProgress(Mask mask, int p, long time) {
		progressBar.setProgress(mask, p, time);
	}

	public void planeDialog(FractalApp app) {
		Dialog d2 = new Dialog(app.getMainFrame(), "Plane Coordinates");

		ComplexPlane plane = app.getComplexPlane();

		d2.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {
				d2.dispose();
			}
		});

		d2.setModal(true);
		d2.setResizable(false);

		d2.setLayout(new FlowLayout());

		d2.setLocationRelativeTo(null);

		TextField txtCenter = new TextField(15);
		txtCenter.setText(String.format("%s", plane.getCenter().format(true)));

		TextField txtRadius = new TextField(10);
		txtRadius.setText(String.format("%s", Util.format(plane.getRadius())));

		Button btnSet = new Button("Set");
		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				Complex center = Complex.parseComplex(txtCenter.getText());
				double radius = Double.parseDouble(txtRadius.getText());
				app.setComplexPlaneCoordinates(center, radius);
				d2.dispose();
			}
		});

		d2.add(new Label("Center: "));
		d2.add(txtCenter);
		d2.add(new Label("Radius: "));
		d2.add(txtRadius);
		d2.add(btnSet);

		d2.pack();
		d2.setVisible(true);
	}

}
