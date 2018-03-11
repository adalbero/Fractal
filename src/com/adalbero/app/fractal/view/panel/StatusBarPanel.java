package com.adalbero.app.fractal.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Progress;

public class StatusBarPanel extends JPanel implements NotificationListener {

	private static final long serialVersionUID = 1L;

	private FractalController controller;
	private JLabel lblMessage;

	public StatusBarPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Color.LIGHT_GRAY);

		controller = FractalController.getInstance();

		lblMessage = new JLabel();

		this.add(lblMessage);
		this.setPreferredSize(new Dimension(30, 30));
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (event == NotificationEvent.FRACTAL_CHANGED) {
			lblMessage.setText(controller.getFractal().getName());
		} else if (event == NotificationEvent.PROGRESS_CHANGED) {
			Progress progress = (Progress) param;
			if (progress.isDone()) {
				lblMessage.setText(String.format("Time: %.2f secs", progress.getTimeInSecs()));
			} else {
				lblMessage.setText(String.format("Progress: %d%%", progress.getProgressInt()));
			}
		}
	}

}
