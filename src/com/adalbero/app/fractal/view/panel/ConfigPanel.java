package com.adalbero.app.fractal.view.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.functions.IteratedFunction;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.view.forms.CartesianPlaneForm;
import com.adalbero.app.fractal.view.forms.ComplexPlaneForm;
import com.adalbero.app.fractal.view.forms.DetailsForm;
import com.adalbero.app.fractal.view.forms.FormPanel;
import com.adalbero.app.fractal.view.forms.PaletteForm;
import com.adalbero.app.fractal.view.forms.ParamsForm;
import com.adalbero.app.fractal.view.forms.TransformForm;

public class ConfigPanel extends JPanel implements NotificationListener {

	private static final long serialVersionUID = 1L;

	List<FormPanel> forms = new ArrayList<>();

	public ConfigPanel() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new GridBagLayout());
	}

	private void addComponent(Component component, double weight) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.weighty = weight;
		c.weightx = 1;
		this.add(component, c);
	}

	private void update() {
		removeForms();

		forms = new ArrayList<>();

		if (FractalController.getInstance().getFractal() instanceof IteratedFunction) {
			forms.add(new CartesianPlaneForm());
			forms.add(new TransformForm());
		} else {
			forms.add(new ComplexPlaneForm());
			forms.add(new DetailsForm());
			forms.add(new ParamsForm());
			forms.add(new PaletteForm());
		}

		for (FormPanel form : forms) {
			addComponent(form, 0);
		}

		addComponent(new JLabel(), 1);
	}

	private void removeForms() {
		for (FormPanel form : forms) {
			form.onRemove();
		}

		this.removeAll();
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (event == NotificationEvent.FRACTAL_CHANGED) {
			update();
		}

		for (FormPanel form : forms) {
			form.onNotification(source, event, param);
		}

	}

}
