package com.adalbero.app.fractal.model;

import java.util.ArrayList;
import java.util.List;

public class TransformationSet {
	private List<Integer> weights = new ArrayList<>();
	private List<Transformation> set = new ArrayList<>();
	
	public TransformationSet() {
	}
	
	public void addTransformation(Transformation t, int weight) {
		weights.add(weight);
		set.add(t);
	}
	
	public Transformation getRandomTransformation() {
		int n = 0;
		
		for (Integer i : weights) {
			n += i;
		}
		
		int r = (int)(Math.random() * n);
		int w = 0;
		
		for (int i = 0; i<set.size(); i++) {
			w = w + weights.get(i);
			if (r <= w) {
				return set.get(i);
			}
		}
		
		return set.get(set.size()-1);
	}
	
	public int getSize() {
		return set.size();
	}
	
	public Transformation getTransformation(int i) {
		return set.get(i);
	}
	
	public int getWeight(int i) {
		return weights.get(i);
	}
	
	public void setWeight(int i, int w) {
		weights.set(i, w);
	}
	
}
