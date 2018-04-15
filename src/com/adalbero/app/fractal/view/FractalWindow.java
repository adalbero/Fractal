package com.adalbero.app.fractal.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.controller.NotificationManager;
import com.adalbero.app.fractal.functions.IteratedFunction;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.view.canvas.GridCanvas;
import com.adalbero.app.fractal.view.canvas.IteratedCanvas;
import com.adalbero.app.fractal.view.canvas.PaintCanvas;
import com.adalbero.app.fractal.view.panel.ConfigPanel;
import com.adalbero.app.fractal.view.panel.SplitPanel;
import com.adalbero.app.fractal.view.panel.StatusBarPanel;
import com.adalbero.app.fractal.view.panel.ToolBarPanel;

public class FractalWindow implements NotificationListener {
	private JFrame mainFrame;
	private ToolBarPanel toolBar;
	private StatusBarPanel statusBar;
	private ConfigPanel configPanel;
	private PaintCanvas canvasPanel;
	private SplitPanel splitPanel;

	public FractalWindow() {
		mainFrame = new JFrame("Fractals");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());

		NotificationManager.getInstance().setMasterListener(this);

		statusBar = new StatusBarPanel();
		configPanel = new ConfigPanel();
		canvasPanel = new GridCanvas();
		splitPanel = new SplitPanel(configPanel, canvasPanel);
		toolBar = new ToolBarPanel();

		mainFrame.add(toolBar, BorderLayout.PAGE_START);
		mainFrame.add(statusBar, BorderLayout.PAGE_END);
		mainFrame.add(splitPanel, BorderLayout.CENTER);

	}

	public void show() {
		mainFrame.setSize(1200, 800);
		mainFrame.setVisible(true);
	}

	public void update() {
		if (FractalController.getInstance().getFractal() instanceof IteratedFunction) {
			canvasPanel = new IteratedCanvas();
		} else {
			canvasPanel = new GridCanvas(); 
		}
		
		splitPanel.setRightComponent(canvasPanel);
		splitPanel.setDividerLocation(450);
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (event == NotificationEvent.FRACTAL_CHANGED) {
			update();
		}

		statusBar.onNotification(source, event, param);
		configPanel.onNotification(source, event, param);
		canvasPanel.onNotification(source, event, param);
	}
}
