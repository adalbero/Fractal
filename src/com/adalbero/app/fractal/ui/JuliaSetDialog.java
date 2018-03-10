package com.adalbero.app.fractal.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.adalbero.app.fractal.FractalApp;
import com.adalbero.app.fractal.data.Complex;
import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.functions.JuliaSet;

public class JuliaSetDialog extends Dialog {

	private static final long serialVersionUID = 1L;

	private FractalPanel fractalPanel;

	private StatusBar statusBar;
	
	public JuliaSetDialog(FractalApp app) {
		super(app.getMainFrame());
		this.setTitle("Julia Set");

		addListeners(app);

		app.setJuliaSetDialog(this);
		
		Fractal f = new JuliaSet();
		Complex c = f.getParams().getComplex(JuliaSet.C);
		
		setLayout(new BorderLayout());
		fractalPanel = new FractalPanel(f, null, null);
		
		statusBar = new StatusBar();
		statusBar.setPreferredSize(new Dimension(20, 20));
		setMessage(c);
		
		this.add(fractalPanel, BorderLayout.CENTER);
		this.add(statusBar, BorderLayout.PAGE_END);
		
		this.setSize(300, 300);
		
		this.setModal(false);
		this.setResizable(true);

	}

	public void addListeners(FractalApp app) {
		Dialog me = this;

		me.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {
				app.setJuliaSetDialog(null);
				me.dispose();
			}
		});
	}

	public void update(Complex c) {
		fractalPanel.getFractal().getParams().setParam(JuliaSet.C, c);
		fractalPanel.repaintFractal();
		setMessage(c);
	}
	
	private void setMessage(Complex c) {
		statusBar.setText("  f(z) = z^2 + (" + c + ")");
	}
}
