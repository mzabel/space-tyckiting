package com.marmar;

public class DistancePosition {
	private int x;
	private int y;
	private int distance;
	
	public DistancePosition(int x, int y, int distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return "DistancePosition [x=" + x + ", y=" + y + ", distance="
				+ distance + "]";
	}
}
