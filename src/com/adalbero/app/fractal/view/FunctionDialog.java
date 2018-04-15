package com.adalbero.app.fractal.view;

import java.awt.Frame;

import javax.swing.JPanel;

import com.adalbero.app.fractal.functions.Newton;
import com.adalbero.app.fractal.model.Complex;
import com.adalbero.app.fractal.view.canvas.FunctionCanvas;

public class FunctionDialog extends DetailDialog {

	private static final long serialVersionUID = 1L;

	private FunctionCanvas functionPanel;

	public FunctionDialog(Frame frame, Newton f) {
		super(frame, f);

		this.setTitle("2D Function");
		this.setSize(800, 600);

	}

	@Override
	public void update(Complex target) {
		functionPanel.setTarget(target);
	}

	@Override
	public JPanel getMainPanel() {
		functionPanel = new FunctionCanvas((Newton) getFractal());
		return functionPanel;
	}

}