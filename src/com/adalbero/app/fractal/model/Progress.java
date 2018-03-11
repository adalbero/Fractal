package com.adalbero.app.fractal.model;

public class Progress {
	private float progress;

	private long t0;
	private long time;
	
	private int max;
	
	public Progress(int max) {
		this.max = max;
		startTimer();
	}
	
	public void startTimer() {
		t0 = System.currentTimeMillis();
		time = 0;
	}
	
	public void stopTimer() {
		time = System.currentTimeMillis() - t0;
	}
	
	public long getTime() {
		if (time == 0) {
			return System.currentTimeMillis() - t0;
		} else {
			return time;
		}
	}
	
	public float getTimeInSecs() {
		return getTime() / 1000f;
	}
	
	public boolean mod(int m) {
		return this.getProgressInt() % m == 0;
	}
	
	public void setProgress(int cont) {
		progress = (float)cont / max;
	}
	
	public float getProgress() {
		return progress * 100;
	}
	
	public int getProgressInt() {
		return (int)(progress * 100);
	}
	
	public boolean isDone() {
		return progress >= 1f;
	}
	
	public void done() {
		progress = 1f;
		stopTimer();
	}
	
	@Override
	public String toString() {
		return String.format("%d%%", getProgressInt());
	}
}
