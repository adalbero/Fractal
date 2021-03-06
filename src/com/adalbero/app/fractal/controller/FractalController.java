package com.adalbero.app.fractal.controller;

import java.util.List;

import com.adalbero.app.fractal.functions.Fractal;
import com.adalbero.app.fractal.model.NotificationEvent;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.model.Plane;

public class FractalController {
	private static FractalController instance;

	private FractalList fractalList = new FractalList();

	private Fractal fractal;
	private Plane plane;
	private Palette palette;

	private FractalController() {
	}

	public static FractalController getInstance() {
		if (instance == null) {
			instance = new FractalController();
		}

		return instance;
	}

	public List<Fractal> getFractalList() {
		return fractalList;
	}

	public Fractal getFractal() {
		return fractal;
	}

	public void setFractal(Fractal f, Object source) {
		this.fractal = f;
		this.setPlane(f.getPreferedPlane(), null);
		this.setPalette(f.getPreferedPalette(), null);

		broadcast(source, NotificationEvent.FRACTAL_CHANGED, null);
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane, Object source) {
		this.plane = plane;

		broadcast(source, NotificationEvent.PLANE_CHANGED, null);
	}

	public Palette getPalette() {
		return palette;
	}

	public void setPalette(Palette.Name palette, Object source) {
		this.palette = new Palette(palette);

		broadcast(source, NotificationEvent.PALETTE_CHANGED, null);
	}

	public void broadcast(Object source, NotificationEvent event, Object param) {
		NotificationManager.getInstance().broadcast(source, event, param);
	}

}
