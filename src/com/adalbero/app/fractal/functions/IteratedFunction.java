package com.adalbero.app.fractal.functions;

import java.util.List;

import com.adalbero.app.fractal.model.CartesianPlane;
import com.adalbero.app.fractal.model.Coordinate;
import com.adalbero.app.fractal.model.Palette;
import com.adalbero.app.fractal.model.Palette.Name;
import com.adalbero.app.fractal.model.Plane;
import com.adalbero.app.fractal.model.Progress;
import com.adalbero.app.fractal.model.Result;
import com.adalbero.app.fractal.model.Rotate;
import com.adalbero.app.fractal.model.Scale;
import com.adalbero.app.fractal.model.Transformation;
import com.adalbero.app.fractal.model.TransformationSet;
import com.adalbero.app.fractal.model.Translate;

public class IteratedFunction extends Fractal {

	private Coordinate p = new Coordinate(0, 0);

	private int maxIterations;

	protected TransformationSet transformations = new TransformationSet();

	public IteratedFunction() {
		addTransformations();

		this.maxIterations = 100000;
	}

	@Override
	public String getName() {
		return "IFS";
	}

	@Override
	public String getFunction() {
		return "Barnsley fern";
	}

	public void addTransformations() {
		transformations.addTransformation(
				new Transformation(new Scale(0, 0.16), new Rotate(0), new Translate(0)), 1);
		transformations.addTransformation(new Transformation(new Scale(0.85), new Rotate(2), new Translate(0, 1.60)),
				85);
		transformations.addTransformation(
				new Transformation(new Scale(0.3), new Rotate(-60), new Translate(0, 1.60)), 7);
		transformations.addTransformation(
				new Transformation(new Scale(-0.3, 0.3), new Rotate(60), new Translate(0, 0.44)), 7);
	}

	@Override
	public Plane getPreferedPlane() {
		return new CartesianPlane(new Coordinate(0, 5), 6f);
	}

	@Override
	public Name getPreferedPalette() {
		return Palette.Name.RAINBOW;
	}

	@Override
	public double getTolerance() {
		return 1e-3;
	}

	@Override
	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int it) {
		this.maxIterations = it;
	}

	public boolean canDraw(Progress progress) {
		return true;
	}

	@Override
	public Result getResult(Coordinate point) {
		return null;
	}

	@Override
	public List<Coordinate> getIterations(Coordinate point, int n) {
		return null;
	}

	public Coordinate getNextPoint() {
		Transformation t = transformations.getRandomTransformation();

		p = t.transform(p);

		return p;
	}

	public TransformationSet getTransformations() {
		return transformations;
	}

}
