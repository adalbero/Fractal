package com.adalbero.app.fractal.view.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.model.NotificationEvent;

public class ParamsForm extends FormPanel {

	private static final long serialVersionUID = 1L;

	FractalController controller;

	private Map<String, JTextField> txtParams;

	public ParamsForm() {
		super("Fractal Parameters");

		ParamsForm me = this;

		controller = FractalController.getInstance();

		Fractal fractal = controller.getFractal();

		addField("Fractal", new JLabel(fractal.getName()));
		addField("Function", new JLabel(fractal.getFunction()));

		txtParams = new HashMap<>();
		for (String param : fractal.getParams().getKeys()) {
			Complex value = fractal.getParams().getComplex(param);
			JTextField textField = new JTextField(value.toString());

			addField(param, textField);
			txtParams.put(param, textField);
		}

		JButton btnSet = new JButton("Set");
		addButton(btnSet);

		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Fractal fractal = controller.getFractal();

				for (String param : txtParams.keySet()) {
					JTextField textField = txtParams.get(param);
					Complex value = Complex.parseComplex(textField.getText());
					fractal.getParams().setParam(param, value);
				}
				controller.broadcast(me, NotificationEvent.FRACTAL_PARAM_CHANGED, null);
			}
		});
	}

}
