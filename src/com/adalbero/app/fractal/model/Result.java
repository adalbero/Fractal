package com.adalbero.app.fractal.model;

public class Result {
	public int iteraction;
	public int rootNum;
	
	public static Result NULL = new Result();
	
	public Result() {
		iteraction = -1;
		rootNum = -1;
	}

	public Result(int i, int r) {
		iteraction = i;
		rootNum = r;
	}

	public Result(int i) {
		iteraction = i;
		rootNum = -1;
	}
	
	public boolean isNull() {
		return iteraction == -1;
	}
	
	@Override
	public String toString() {
		String msg = "Result: {";
		msg += "it:" + iteraction;
		if (rootNum > 0) {
			msg += " root#:" + rootNum;
		}
		msg += "} ";
		
		return msg;
	}
}
