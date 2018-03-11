package com.adalbero.app.fractal.view;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.model.Complex;

public abstract class DetailDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private Fractal fractal;

	public DetailDialog(Frame frame, Fractal fractal) {
		super(frame);

		this.fractal = fractal;

		setLayout(new BorderLayout());

		JPanel panel = getMainPanel();

		this.add(panel, BorderLayout.CENTER);

		this.setSize(300, 300);

		this.setModal(false);
		this.setResizable(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public Fractal getFractal() {
		return this.fractal;
	}

	public abstract void update(Complex c);

	public abstract JPanel getMainPanel();
}
