package com.adalbero.app.fractal.view.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.functions.JuliaSet;
import com.adalbero.app.fractal.functions.Newton;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.ComplexPlane;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Result;
import com.adalbero.app.fractal.view.DetailDialog;
import com.adalbero.app.fractal.view.FunctionDialog;
import com.adalbero.app.fractal.view.GraphDialog;
import com.adalbero.app.fractal.view.JuliaSetDialog;
import com.adalbero.app.fractal.view.MapDialog;

public class DetailsForm extends FormPanel implements NotificationListener {

	private static final long serialVersionUID = 1L;

	private FractalController controller;

	private JTextField txtTarget;
	private JLabel txtIterations;
	private JLabel txtRoot;

	private DetailDialog mapDialog;
	private DetailDialog juliaSetDialog;
	private DetailDialog graphDialog;
	private DetailDialog functionDialog;
	private List<DetailDialog> dialogs = new ArrayList<>();

	public DetailsForm() {
		super("Details");

		controller = FractalController.getInstance();

		txtTarget = new JTextField();
		addField("Target", txtTarget);

		txtIterations = new JLabel();
		addField("Iterations", txtIterations);

		if (controller.getFractal().getRoots() != null) {
			txtRoot = new JLabel();
			addField("Root", txtRoot);
		}

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
		
		if (controller.getFractal().getName().equals("Mandelbrot")) {
			JButton btnJuliaSet = new JButton("JuliaSet");
			btnJuliaSet.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (juliaSetDialog == null) {
						juliaSetDialog = new JuliaSetDialog(null, new JuliaSet());
						dialogs.add(juliaSetDialog);
					}

					juliaSetDialog.setVisible(true);
					updateDialogs();
				}
			});
			addButton2(btnJuliaSet);
		}

		if (controller.getFractal() instanceof Newton) {
			Newton newton = (Newton) controller.getFractal();

			JButton btnFunction = new JButton("2D");
			btnFunction.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (functionDialog == null) {
						functionDialog = new FunctionDialog(null, newton);
						dialogs.add(functionDialog);
					}

					functionDialog.setVisible(true);
					updateDialogs();
				}
			});
			addButton2(btnFunction);
		}

		JButton btnIterations = new JButton("Iterations");
		btnIterations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (graphDialog == null) {
					graphDialog = new GraphDialog(null, controller.getFractal());
					dialogs.add(graphDialog);
				}

				graphDialog.setVisible(true);
				updateDialogs();
			}
		});
		addButton2(btnIterations);

		JButton btnMap = new JButton("Map");
		btnMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mapDialog == null) {
					mapDialog = new MapDialog(null, controller.getFractal());
					dialogs.add(mapDialog);
				}

				mapDialog.setVisible(true);
				updateDialogs();
			}
		});
		addButton2(btnMap);

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
			Complex target = complexPlane.getComplexTarget();
			txtTarget.setText(target.format(true));

			Fractal f = controller.getFractal();
			Result result = f.getResult(target);
			int it = result.iteraction;
			int r = result.rootNum;
			txtIterations.setText("" + (result.iteraction < 0 ? "MAX" : it));

			List<Complex> roots = f.getRoots();
			if (roots != null && roots.size() > 0 && r < roots.size()) {
				Complex root = f.getRoot(r);
				txtRoot.setText("#" + (r + 1) + " of " + roots.size() + " (" + root + ")");
			}

		}
	}

	private void updateDialogs() {
		Complex target = new Complex(controller.getPlane().getTarget());

		for (DetailDialog dialog : dialogs) {
			dialog.update(target);
		}
	}

	@Override
	public void onRemove() {
		for (DetailDialog dialog : dialogs) {
			dialog.dispose();
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

}
