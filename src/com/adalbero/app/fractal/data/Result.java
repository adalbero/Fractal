package com.adalbero.app.fractal.data;

public class Result {
	public int iteraction;
	public int root;
	
	public static Result NULL = new Result();
	
	public Result() {
		iteraction = -1;
		root = 0;
	}

	public Result(int i, int r) {
		iteraction = i;
		root = r;
	}

	public Result(int i) {
		iteraction = i;
		root = 0;
	}
	
	public boolean isNull() {
		return iteraction == -1;
	}
	
	@Override
	public String toString() {
		String msg = "Result: {";
		msg += "it:" + iteraction;
		if (root > 0) {
			msg += " root:" + root;
		}
		msg += "} ";
		
		return msg;
	}
}
