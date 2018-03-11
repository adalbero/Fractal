package com.adalbero.app.fractal.controller;

import com.adalbero.app.fractal.model.NotificationEvent;

public interface NotificationListener {
	public void onNotification(Object source, NotificationEvent event, Object param);
}
