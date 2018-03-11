package com.adalbero.app.fractal.view.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.functions.JuliaSet;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.ComplexPlane;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.view.DetailDialog;
import com.adalbero.app.fractal.view.GraphDialog;
import com.adalbero.app.fractal.view.JuliaSetDialog;
import com.adalbero.app.fractal.view.MapDialog;

public class DetailsForm extends FormPanel implements NotificationListener {

	private static final long serialVersionUID = 1L;

	private FractalController controller;

	private JTextField txtTarget;
	private DetailDialog mapDialog;
	private DetailDialog juliaSetDialog;
	private DetailDialog graphDialog;

	public DetailsForm() {
		super("Details");

		controller = FractalController.getInstance();

		txtTarget = new JTextField();
		addField("Target", txtTarget);

		if (controller.getFractal().getName().equals("Mandelbrot")) {
			JButton btnJuliaSet = new JButton("JuliaSet");
			btnJuliaSet.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (juliaSetDialog == null) {
						juliaSetDialog = new JuliaSetDialog(null, new JuliaSet());
					}

					juliaSetDialog.setVisible(true);
					updateDialogs();
				}
			});
			addButton(btnJuliaSet);
		}

		JButton btnIterations = new JButton("Iterations");
		btnIterations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (graphDialog == null) {
					graphDialog = new GraphDialog(null, controller.getFractal());
				}

				graphDialog.setVisible(true);
				updateDialogs();
			}
		});
		addButton(btnIterations);

		JButton btnMap = new JButton("Map");
		btnMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mapDialog == null) {
					mapDialog = new MapDialog(null, controller.getFractal());
				}

				mapDialog.setVisible(true);
				updateDialogs();
			}
		});
		addButton(btnMap);

		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ComplexPlane complexPlane = getComplexPlane();
				if (complexPlane != null) {
					Complex target = Complex.parseComplex(txtTarget.getText());
					complexPlane.setTarget(target);
					controller.broadcast(this, NotificationEvent.PLANE_TARGET_CHANGED, null);
				}
			}
		});
		addButton(btnSet);
	}

	private ComplexPlane getComplexPlane() {
		Plane plane = FractalController.getInstance().getPlane();
		if (plane instanceof ComplexPlane) {
			return (ComplexPlane) plane;
		}
		return null;
	}

	private void update() {
		ComplexPlane complexPlane = getComplexPlane();
		if (complexPlane != null) {
			txtTarget.setText(complexPlane.getComplexTarget().format(true));
		}
	}

	private void updateDialogs() {
		Complex target = new Complex(controller.getPlane().getTarget());

		if (mapDialog != null) {
			mapDialog.update(target);
		}

		if (juliaSetDialog != null) {
			juliaSetDialog.update(target);
		}

		if (graphDialog != null) {
			graphDialog.update(target);
		}
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (source == this)
			return;

		if (event == NotificationEvent.PLANE_TARGET_CHANGED || event == NotificationEvent.PLANE_CHANGED
				|| event == NotificationEvent.FRACTAL_CHANGED) {
			update();
		}

		if (event == NotificationEvent.PLANE_TARGET_CHANGED) {
			updateDialogs();
		}

	}

	@Override
	public void onRemove() {
		if (juliaSetDialog != null) {
			juliaSetDialog.dispose();
		}

		if (mapDialog != null) {
			mapDialog.dispose();
		}

		if (graphDialog != null) {
			graphDialog.dispose();
		}
	}
}
