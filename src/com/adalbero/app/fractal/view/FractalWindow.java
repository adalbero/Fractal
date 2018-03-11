package com.adalbero.app.fractal.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.controller.NotificationManager;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.view.panel.ConfigPanel;
import com.adalbero.app.fractal.view.panel.ContentPanel;
import com.adalbero.app.fractal.view.panel.PaintPanel;
import com.adalbero.app.fractal.view.panel.StatusBarPanel;
import com.adalbero.app.fractal.view.panel.ToolBarPanel;

public class FractalWindow implements NotificationListener {
	private JFrame mainFrame;
	private ToolBarPanel toolBar;
	private StatusBarPanel statusBar;
	private ConfigPanel configPanel;
	private PaintPanel paintPanel;
	private ContentPanel contentPanel;

	public FractalWindow() {
		mainFrame = new JFrame("Fractals");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());

		NotificationManager.getInstance().setMasterListener(this);

		statusBar = new StatusBarPanel();
		configPanel = new ConfigPanel();
		paintPanel = new PaintPanel();
		contentPanel = new ContentPanel(configPanel, paintPanel);
		toolBar = new ToolBarPanel();

		mainFrame.add(toolBar, BorderLayout.PAGE_START);
		mainFrame.add(statusBar, BorderLayout.PAGE_END);
		mainFrame.add(contentPanel, BorderLayout.CENTER);

	}

	public void show() {
		mainFrame.setSize(1200, 800);
		mainFrame.setVisible(true);
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		statusBar.onNotification(source, event, param);
		configPanel.onNotification(source, event, param);
		paintPanel.onNotification(source, event, param);
	}
}
