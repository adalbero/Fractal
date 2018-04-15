package com.adalbero.app.fractal.view.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.functions.IteratedFunction;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Rotate;
import com.adalbero.app.fractal.model.Scale;
import com.adalbero.app.fractal.model.Transformation;
import com.adalbero.app.fractal.model.Translate;

public class TransformForm extends FormPanel {

	private static final long serialVersionUID = 1L;

	FractalController controller;

	private JTextField txtIterations;
	private JComboBox<String> selFunction;
	private JTextField txtWeight;
	private JTextField txtScale;
	private JTextField txtRotate;
	private JTextField txtTranslate;

	public TransformForm() {
		super("Affine Transformation");

		TransformForm me = this;

		controller = FractalController.getInstance();

		txtIterations = new JTextField();
		addField("Iterations", txtIterations);

		selFunction = new JComboBox<>();
		selFunction.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					updateTransformation(selFunction.getSelectedIndex());
				}
			}
		});
		addField("Transformation", selFunction);

		txtWeight = new JTextField();
		addField("Weight", txtWeight);

		txtScale = new JTextField();
		addField("Scale", txtScale);

		txtRotate = new JTextField();
		addField("Rotate", txtRotate);

		txtTranslate = new JTextField();
		addField("Translate", txtTranslate);

		JButton btnSet = new JButton("Set");
		addButton(btnSet);

		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IteratedFunction f = getFractal();

				int it = Integer.parseInt(txtIterations.getText());
				f.setMaxIterations(it);

				int idx = selFunction.getSelectedIndex();

				int w = Integer.parseInt(txtWeight.getText());
				f.getTransformations().setWeight(idx, w);

				Scale s = new Scale(parse(txtScale.getText()));
				Rotate r = new Rotate(parse(txtRotate.getText()));
				Translate t = new Translate(parse(txtTranslate.getText()));

				f.getTransformations().getTransformation(idx).set(s, r, t);

				controller.broadcast(me, NotificationEvent.FRACTAL_PARAM_CHANGED, null);
			}
		});

		update();
	}

	private Coordinate parse(String str) {
		try {
			double value = Double.parseDouble(str);
			return new Coordinate(value, value);
		} catch (Exception ex) {
		}

		return Coordinate.parseCoordinate(str);
	}

	private void update() {
		IteratedFunction f = getFractal();

		txtIterations.setText("" + f.getMaxIterations());

		int n = f.getTransformations().getSize();
		for (int i = 0; i < n; i++) {
			selFunction.addItem(String.format("Transformation %d", (i + 1)));
		}
	}

	private void updateTransformation(int idx) {
		IteratedFunction f = getFractal();
		Transformation t = f.getTransformations().getTransformation(idx);
		int w = f.getTransformations().getWeight(idx);

		txtWeight.setText("" + w);
		txtScale.setText(t.getScale().format(false));
		txtRotate.setText(t.getRotate().format(false));
		txtTranslate.setText(t.getTranslate().format(false));
	}

	private IteratedFunction getFractal() {
		return (IteratedFunction) controller.getFractal();
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (source == this)
			return;

		if (event == NotificationEvent.FRACTAL_PARAM_CHANGED) {
			update();
		}
	}

}
