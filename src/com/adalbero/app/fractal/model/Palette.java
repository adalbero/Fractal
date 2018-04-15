package com.adalbero.app.fractal.model;

import java.awt.Color;

public class Palette {
	private static int SIZE = 256;

	private Color[] palette;
	private int ordinal;
	private Name name;

	public Palette(Name name) {
		init(name);
	}

	public void draw(Name name) {
		init(name);
	}

	public Name getName() {
		return this.name;
	}

	public void next() {
		Name names[] = Name.values();
		ordinal = (ordinal + 1) % names.length;
		init(names[ordinal]);
	}

	public void previous() {
		Name names[] = Name.values();
		ordinal = (ordinal - 1 + names.length) % names.length;
		init(names[ordinal]);
	}

	public void scrollUp() {
		Color c = palette[0];
		int n = palette.length;
		for (int i = 1; i < n; i++) {
			palette[i - 1] = palette[i];
		}
		palette[n - 1] = c;
	}

	public void scrollDown() {
		int n = palette.length;
		Color c = palette[n - 1];
		for (int i = n - 1; i > 0; i--) {
			palette[i] = palette[i - 1];
		}
		palette[0] = c;
	}

	public void invert() {
		int n = palette.length;
		;
		for (int i = 0; i < n / 2; i++) {
			Color c = palette[i];
			palette[i] = palette[n - i - 1];
			palette[n - i - 1] = c;
		}
	}

	public int getLength() {
		return palette.length;
	}

	public Color getColor(Result result, int roots) {
		if (result == null || result.isNull()) {
			return Color.BLACK;
		}

		if (roots > 0 && roots < palette.length) {
			if (result.rootNum < 0) {
				return Color.BLACK;
			} else {
				int n = palette.length / roots;
				int idx = result.iteraction % n;
				idx = result.rootNum * n + idx;

				return getColor(idx);
			}
		} else {
			return getColor(result.iteraction);
		}
	}

	public Color getColor(int idx) {
		return palette[idx % palette.length];
	}

	public void init(Name name) {
		this.name = name;

		switch (name) {
		case RAINBOW:
			drawRainbow(SIZE, 1);
			break;
		case RAINBOW_MOD2:
			drawRainbow(SIZE, 2);
			break;
		case RAINBOW_MOD6:
			drawRainbow(SIZE, 6);
			break;
		case BW:
			drawGradient(SIZE, 1, Color.BLACK, Color.WHITE);
			break;
		case BW_MOD2:
			drawGradient(SIZE, 2, Color.BLACK, Color.WHITE);
			break;
		case BW_MOD4:
			drawGradient(SIZE, 4, Color.BLACK, Color.WHITE);
			break;
		case ROOT2:
			drawRoots(SIZE, 1, 2);
			break;
		case ROOT3:
			drawRoots(SIZE, 1, 3);
			break;
		case ROOT3_MOD2:
			drawRoots(SIZE, 2, 3);
			break;
		case ROOT5:
			drawRoots(SIZE, 1, 5);
			break;
		case ROOT6:
			drawRoots(SIZE, 1, 6);
			break;
		case METAL:
			drawGradient(50, 1, Color.WHITE, Color.DARK_GRAY, Color.getHSBColor(100 / 360f, .25f, .50f), Color.WHITE,
					Color.DARK_GRAY, Color.getHSBColor(233 / 360f, .50f, .25f), Color.WHITE, Color.DARK_GRAY,
					Color.getHSBColor(308 / 360f, .25f, .50f), Color.WHITE);
			break;
		}

		ordinal = name.ordinal();
	}

	public void drawRainbow(int size, int zebra) {
		palette = new Color[size];
		for (int i = 0; i < size; i++) {
			float p = 1f / size * i;

			palette[i] = Color.getHSBColor(p, 1f, 1f);
		}

		if (zebra > 1) {
			applyZebra(zebra);
		}
	}

	public void drawGradient(int size, int zebra, Color... colors) {

		int n = colors.length - 1;
		palette = new Color[size * n];

		for (int j = 0; j < n; j++) {
			for (int i = 0; i < size; i++) {
				float p = (float) i / size;
				palette[j * size + i] = mixColors(colors[j], colors[j + 1], p);
			}
		}

		if (zebra > 1) {
			applyZebra(zebra);
		}
	}

	public void drawRoots(int size, int zebra, int roots) {

		palette = new Color[size * roots];

		for (int j = 0; j < roots; j++) {
			float h = (float) j / roots;

			Color c1 = Color.getHSBColor(h, 1f, 1f);
			Color c2 = Color.getHSBColor(h, 0.5f, 1f);
			for (int i = 0; i < size; i++) {
				float p = (float) i / size;
				palette[j * size + i] = mixColors(c1, c2, p);
			}
		}

		if (zebra > 1) {
			applyZebra(zebra);
		}
	}

	public void applyZebra(int zebra) {
		int size = palette.length;

		for (int i = 0; i < size; i++) {
			float d = i % zebra; // inside step
			float p = 1 - ((d / zebra) / 2f);

			Color c = palette[i];
			float[] hsb = getHSB(c);
			palette[i] = Color.getHSBColor(hsb[0], hsb[1], hsb[2] * p);
		}
	}

	private float[] getHSB(Color c) {
		return Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
	}

	private Color mixColors(Color c1, Color c2, double p) {
		double np = 1.0 - p;

		int r = (int) (c1.getRed() * np + c2.getRed() * p);
		int g = (int) (c1.getGreen() * np + c2.getGreen() * p);
		int b = (int) (c1.getBlue() * np + c2.getBlue() * p);

		return new Color(r, g, b);
	}

	public enum Name {
		RAINBOW, RAINBOW_MOD2, RAINBOW_MOD6, BW, BW_MOD2, BW_MOD4, ROOT2, ROOT3, ROOT3_MOD2, ROOT5, ROOT6, METAL
	};
}
