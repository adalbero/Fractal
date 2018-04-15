package com.adalbero.app.fractal.view.forms;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.view.canvas.PaletteCanvas;

public class PaletteForm extends FormPanel implements NotificationListener {

	private static final long serialVersionUID = 1L;

	FractalController controller;

	private JComboBox<Palette.Name> selPalette;
	private PaletteCanvas palettePanel;

	public PaletteForm() {
		super("Palette");

		controller = FractalController.getInstance();

		selPalette = new JComboBox<>();

		for (Palette.Name name : Palette.Name.values()) {
			selPalette.addItem(name);
		}

		selPalette.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Palette.Name name = (Palette.Name) selPalette.getSelectedItem();
					controller.setPalette(name, this);
					controller.broadcast(this, NotificationEvent.PALETTE_CHANGED, null);
				}
			}
		});

		addField("Name", selPalette);

		update();
	}

	public JPanel getFirstPanel() {
		palettePanel = new PaletteCanvas();
		palettePanel.setPreferredSize(new Dimension(30, 30));

		return palettePanel;
	}

	private void update() {
		Palette.Name name = controller.getPalette().getName();
		selPalette.setSelectedItem(name);
		palettePanel.onNotification(this, NotificationEvent.PALETTE_CHANGED, null);
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (source == this)
			return;

		if (event == NotificationEvent.PALETTE_CHANGED) {
			update();
		}

	}
}
