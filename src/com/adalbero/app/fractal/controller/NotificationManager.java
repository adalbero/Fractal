package com.adalbero.app.fractal.controller;

import com.adalbero.app.fractal.model.NotificationEvent;

public class NotificationManager {
	private static NotificationManager instance;

	private NotificationListener listener;
	
	private NotificationManager() {

	}

	public static NotificationManager getInstance() {
		if (instance == null) {
			instance = new NotificationManager();
		}

		return instance;
	}

	public void setMasterListener(NotificationListener listener) {
		this.listener = listener;
	}
	
	public void broadcast(Object source, NotificationEvent event, Object param) {
//		System.out.println(event + " - " + source);

		if (listener != null && source != null) {
			listener.onNotification(source, event, param);
		}
	}
}
