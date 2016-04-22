package com.marmar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import tyckiting.Position;

public class Grid {
	private int offset;
	private Field[][] fields;
	private ArrayList<Field> fieldList;
	
	public Grid(int mapSize) {
		offset=mapSize;
		fields=new Field[mapSize*2+1][mapSize*2+1];
		fieldList=new ArrayList<>();
		for(Position p:GridUtil.iterateRadius(0, 0, mapSize)) {
			Field f=new Field(p.x(),p.y());
			fields[offset+p.x()][offset+p.y()]=f;
			fieldList.add(f);
		}
		fieldList.trimToSize();
		for(Field f:fieldList) {
			f.learnNeighbors(this);
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
		for(Field f:fieldList)
				f.nextTurn();
		for(Field f:fieldList)
			f.blur(maxMove);
	}

	public void addHitZoneProb(Position pos, double strength) {
		getField(pos).addHitZoneProb(strength);
	}

	public void addEnemyProb(Position pos, double strength) {
		getField(pos).addEnemyProb(strength);
	}

	public double getHitZoneProb(Position pos) {
		return getField(pos).getHitZoneProb();
	}

	public List<Field> getBestEnemyProbs() {
		ArrayList<Field> l=new ArrayList<>(fieldList);
		Collections.shuffle(l);
		return l.stream()
			.sorted((a,b)->-Double.compare(a.getEnemyProb(), b.getEnemyProb()))
			.collect(Collectors.toList());
	}

	public Field getField(Position pos) {
		return getField(pos.x(), pos.y());
	}

	public void setEnemyProb(Position pos, double value) {
		Field f=getField(pos);
		if(f!=null)
			f.setEnemyProb(value);
	}
}
