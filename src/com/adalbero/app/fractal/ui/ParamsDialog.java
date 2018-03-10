package com.adalbero.app.fractal.ui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.adalbero.app.fractal.FractalApp;
import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.functions.Fractal;

public class ParamsDialog extends Dialog {

	private static final long serialVersionUID = 1L;

	public ParamsDialog(FractalApp app) {
		super(app.getMainFrame(), "Set Fractal Param");

		Fractal fractal = app.getFractal();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {
				close();
			}
		});

		this.setLayout(new FlowLayout());

		TextField txtValue = new TextField(20);

		Choice choiceKey = new Choice();

		for (String key : fractal.getParams().getKeys()) {
			choiceKey.add(key);
		}

		choiceKey.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemevent) {
				String key = choiceKey.getSelectedItem();
				Complex value = fractal.getParams().getComplex(key);
				txtValue.setText(value.toString());
			}
		});
		choiceKey.select(0);
		String key = choiceKey.getSelectedItem();
		Complex value = fractal.getParams().getComplex(key);
		txtValue.setText(value.toString());

		Button btnSet = new Button("Set");
		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				String key = choiceKey.getSelectedItem();
				String value = txtValue.getText();
				fractal.getParams().setParam(key, value);
				app.repaintFractal();
			}
		});

		this.add(choiceKey);
		this.add(txtValue);
		this.add(btnSet);

		this.pack();
	}

	public void showModalLess(Component component) {
		this.setLocationRelativeTo(component);

		this.setModal(false);
		this.setResizable(false);

		this.setVisible(true);

	}

	private void close() {
		this.dispose();
	}
}
