package com.adalbero.app.fractal.view.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.adalbero.app.fractal.controller.FractalController;
import com.adalbero.app.fractal.controller.NotificationListener;
import com.adalbero.app.fractal.model.CartesianPlane;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Util;

public class CartesianPlaneForm extends FormPanel implements NotificationListener {

	private static final long serialVersionUID = 1L;

	FractalController controller;

	private JTextField txtCenter;
	private JTextField txtRadius;

	public CartesianPlaneForm() {
		super("Cartesian Plane");

		controller = FractalController.getInstance();

		txtCenter = new JTextField();
		txtRadius = new JTextField();

		addField("Center", txtCenter);
		addField("Radius", txtRadius);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CartesianPlane plane = getCartesianPlane();
				if (plane != null) {
					plane.reset();
					updateAndBroadcast();
				}
			}
		});
		addButton(btnReset);

		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				CartesianPlane plane = getCartesianPlane();
				if (plane != null) {
					Coordinate center = Coordinate.parseCoordinate(txtCenter.getText());
					double radius = Double.parseDouble(txtRadius.getText());
					plane.setCenter(center);
					plane.setRadius(radius);
					updateAndBroadcast();
				}
			}
		});
		addButton(btnSet);

		JButton btnZoomIn = new JButton("Zoom In");
		btnZoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CartesianPlane cartesianPlane = getCartesianPlane();
				if (cartesianPlane != null) {
					cartesianPlane.zoom(0.5);
					updateAndBroadcast();
				}
			}
		});
		addButton2(btnZoomIn);

		JButton btnZoomOut = new JButton("Zoom Out");
		btnZoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CartesianPlane cartesianPlane = getCartesianPlane();
				if (cartesianPlane != null) {
					cartesianPlane.zoom(2);
					updateAndBroadcast();
				}
			}
		});
		addButton2(btnZoomOut);

		Plane plane = controller.getPlane();
		if (plane != null) {
			update();
		}

	}

	private CartesianPlane getCartesianPlane() {
		Plane plane = FractalController.getInstance().getPlane();
		if (plane instanceof CartesianPlane) {
			CartesianPlane cartesianPlane = (CartesianPlane) plane;
			return cartesianPlane;
		}
		return null;
	}

	private void updateAndBroadcast() {
		update();
		controller.broadcast(this, NotificationEvent.PLANE_CHANGED, null);
	}

	private void update() {
		Plane plane = controller.getPlane();
		if (plane instanceof CartesianPlane) {
			CartesianPlane cartesianPlane = (CartesianPlane) plane;

			txtCenter.setText(cartesianPlane.getCenter().format(true));
			txtRadius.setText(Util.format(cartesianPlane.getRadius()));
		}
	}

	@Override
	public void onNotification(Object source, NotificationEvent event, Object param) {
		if (this == source)
			return;

		if (event == NotificationEvent.PLANE_CHANGED) {
			update();
		} else if (event == NotificationEvent.PLANE_TARGET_CHANGED) {
			update();
		}
	}

}
