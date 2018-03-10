package com.adalbero.app.fractal.ui;

import java.awt.Color;
import java.awt.Label;

public class StatusBar extends Label {

	private static final long serialVersionUID = 1L;

	public StatusBar() {
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	public void setMessage(String msg) {
		this.setText(msg);
	}
}
