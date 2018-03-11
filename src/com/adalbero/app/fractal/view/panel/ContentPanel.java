package com.adalbero.app.fractal.view.panel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class ContentPanel extends JSplitPane {
	private static final long serialVersionUID = 1L;

	public ContentPanel(Component left, Component right) {
		super(JSplitPane.HORIZONTAL_SPLIT);

		Dimension minimumSize = new Dimension(200, 50);

		left = new JScrollPane(left);
		left.setMinimumSize(minimumSize);
		this.setLeftComponent(left);

		right.setMinimumSize(minimumSize);
		this.setRightComponent(right);

		this.setOneTouchExpandable(true);
		this.setDividerLocation(400);
	}

}
