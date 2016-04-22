package com.marmar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Field {
	private int x;
	private int y;
	private List<Set<Field>> neighborRings;
	private double hitZoneProb=0.0;
	private double enemyProb=0.0;
	private double lastHitZoneProb=0.01;
	private double lastEnemyProb=0.01;
	
	public Field(int x, int y) {
		this.x=x;
		this.y=y;
		
	}

	public void learnNeighbors(Grid grid) {
		neighborRings=new ArrayList<>();
		for(int i=0;i<=grid.getMapSize();i++)
			neighborRings.add(new HashSet<Field>());
		for(DistancePosition dp:GridUtil.iterateRings(x, y, grid.getMapSize())) {
			Field f=grid.getField(dp.getX(), dp.getY());
			if(f!=null)
				neighborRings.get(dp.getDistance()).add(f);
		}
	}

	public Set<Field> getNeighbors(int distance) {
		return neighborRings.get(distance);
	}

	@Override
	public String toString() {
		return "F[x=" + x + ", y=" + y + "]";
	}

	public void nextTurn() {
		lastEnemyProb=enemyProb;
		lastHitZoneProb=hitZoneProb;
	}

	public void blur(int maxMove) {
		
	}
	
	
		
}
