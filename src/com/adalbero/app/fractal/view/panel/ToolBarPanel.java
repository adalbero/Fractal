package com.adalbero.app.fractal.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.functions.Fractal;

public class ToolBarPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> selFractal;

	public ToolBarPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Color.LIGHT_GRAY);

		FractalController controller = FractalController.getInstance();

		selFractal = new JComboBox<>();

		selFractal.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					FractalController controller = FractalController.getInstance();

					int idx = selFractal.getSelectedIndex();
					Fractal f = controller.getFractalList().get(idx);
					controller.setFractal(f, this);
				}
			}
		});

		for (Fractal f : controller.getFractalList()) {
			selFractal.addItem(f.toString());
		}
//		selFractal.setSelectedIndex(0);
		
		this.add(new JLabel("Fractal:"), null);
		this.add(selFractal, null);

		this.setPreferredSize(new Dimension(0, 35));
	}

}
