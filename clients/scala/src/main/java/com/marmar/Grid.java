package com.marmar;

import java.util.List;

import tyckiting.Position;

public class Grid {
	private int offset;
	private Field[][] fields;
	private List<Position> kernel;
	
	public Grid(int mapSize) {
		offset=mapSize;
		fields=new Field[mapSize*2+1][mapSize*2+1];
		for(Position p:GridUtil.iterateRadius(0, 0, mapSize))
			fields[offset+p.x()][offset+p.y()]=new Field(p.x(),p.y());
		for(Field[] row:fields) {
			for(Field f:row) {
				if(f!=null) {
					f.learnNeighbors(this);
				}
			}
		}
	}
	
	public Field getField(int x, int y) {
		int ox=offset+x;
		int oy=offset+y;
		if(ox>=0&&ox<fields.length &&oy>=0&&oy<fields.length)
			return fields[ox][oy];
		else
			return null;
	}

	public int getMapSize() {
		return fields.length;
	}
	
	public static void main(String[] args) {
		Field f=new Grid(14).getField(-14, 4);
		
		for(int i=0;i<5;i++) {
			System.out.println(f.getNeighbors(i));
		}
	}
	
	public void nextTurn(int maxMove) {
		for(Field[] row:fields) {
			for(Field f:row) {
				if(f!=null) {
					f.nextTurn();
				}
			}
		}
		for(Field[] row:fields) {
			for(Field f:row) {
				if(f!=null) {
					f.blur(maxMove);
				}
			}
		}
	}

	public void addHitZoneProb(Position pos, double strength) {
		// TODO Auto-generated method stub
		
	}

	public void addEnemyProb(Position pos, double strength) {
		// TODO Auto-generated method stub
		
	}

	public double getHitZoneProb(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getBestHitZoneProb() {
		// TODO Auto-generated method stub
		return 0;
	}
}