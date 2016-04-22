package com.marmar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tyckiting.Position;

public class Field {
	private int x;
	private int y;
	private List<Set<Field>> neighborRings;
	private double hitZoneProb=0.01;
	private double enemyProb=0.01;
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
		enemyProb=0;
		hitZoneProb=0;
		int enemyNeighbors=0;
		int hitZoneNeighbors=0;
		for(int i=0;i<=maxMove;i++) {
			for(Field n:neighborRings.get(i)) {
				enemyNeighbors++;
				hitZoneNeighbors++;
				enemyProb+=n.lastEnemyProb;
				hitZoneProb+=n.lastHitZoneProb;
				if(i==1) {
					hitZoneNeighbors++;
					hitZoneProb+=n.lastHitZoneProb;
				}
				else if(i==maxMove) {
					enemyNeighbors++;
					enemyProb+=n.lastEnemyProb;
				}
			}
		}
		enemyProb/=enemyNeighbors;
		hitZoneProb/=hitZoneNeighbors;
		enemyProb*=0.75;
		hitZoneProb*=0.75;
	}

	public void addHitZoneProb(double strength) {
		hitZoneProb+=strength;
	}

	public void addEnemyProb(double strength) {
		enemyProb+=strength;
	}

	public double getHitZoneProb() {
		return hitZoneProb;
	}

	public double getEnemyProb() {
		return enemyProb;
	}

	public Position getPos() {
		return new Position(x, y);
	}
	
	
		
}
