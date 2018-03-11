package com.adalbero.app.fractal.view.forms;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.model.NotificationEvent;

public class FormPanel extends JPanel implements NotificationListener {

	private static final long serialVersionUID = 1L;

	private JPanel formPanel;
	private JPanel buttonPanel;
	private JPanel buttonPanel2;

	private int line = 0;

	public FormPanel(String title) {
		this.setLayout(new GridBagLayout());

		formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());

		TitledBorder border;
		border = BorderFactory.createTitledBorder(title);
		this.setBorder(border);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		buttonPanel2 = new JPanel();
		buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel2.setPreferredSize(new Dimension(0, 0));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.weighty = 1;
		c.weightx = 1;
		c.insets = new Insets(2, 2, 2, 2);

		JPanel firstPanel = getFirstPanel();
		if (firstPanel != null) {
			this.add(firstPanel, c);
		}
		this.add(formPanel, c);
		this.add(buttonPanel, c);
		this.add(buttonPanel2, c);

	}
	
	public JPanel getFirstPanel() {
		return null;
	}

	public void addButton(JButton button) {
		buttonPanel.add(button);
	}

	public void addButton2(JButton button) {
		buttonPanel2.add(button);
		buttonPanel2.setPreferredSize(buttonPanel.getPreferredSize());
	}

	public void addField(String label, Component component) {
		GridBagConstraints c = new GridBagConstraints();

		c.gridy = line;
		c.weightx = 0;
		c.ipadx = 5;
		c.insets = new Insets(2, 2, 0, 2);

		c.anchor = GridBagConstraints.NORTHEAST;
		c.gridx = 0;
		c.weightx = 0;
		formPanel.add(new JLabel(label + ":"), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.weightx = 1;
		formPanel.add(component, c);

		line++;
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
	}
	
	public void onRemove() {
		
	}
}
