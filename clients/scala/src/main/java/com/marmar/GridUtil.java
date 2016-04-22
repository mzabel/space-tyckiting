package com.marmar;

import java.util.ArrayList;
import java.util.List;

import tyckiting.Position;

public class GridUtil {
	public static List<Position> iterateRadius(int x, int y, int r) {
		List<Position> l=new ArrayList<>();
		for(int i=0;i<=r;i++) {
			for(int sx=-i,sy=i-r;sx<=r;sx++) {
				l.add(new Position(x+sx,y+sy));
			}
			
			if(i!=0) {
				for(int sx=-r,sy=i;sx<=r-i;sx++) {
					l.add(new Position(x+sx,y+sy));
				}
			}
		}
		return l;
	}
	
	public static List<DistancePosition> iterateRings(int x, int y, int r) {
		List<DistancePosition> l=new ArrayList<>();
		for(int i=0;i<=r;i++) {
			for(int sx=-i,sy=i-r;sx<=r;sx++) {
				l.add(new DistancePosition(x+sx,y+sy, distance(0,0,sx,sy)));
			}
			
			if(i!=0) {
				for(int sx=-r,sy=i;sx<=r-i;sx++) {
					l.add(new DistancePosition(x+sx,y+sy, distance(0,0,sx,sy)));
				}
			}
		}
		return l;
	}
	
	public static int distance(int x1, int y1, int x2, int y2) {
		return (	Math.abs(x1 - x2) 
		          + Math.abs(x1 + y1 - x2 - y2)
		          + Math.abs(y1 - y2)) / 2;
	}
	
	public static void main(String[] args) {
		System.out.println(iterateRings(0, 0, 2));
	}

	public static int distance(Position a, Position b) {
		return (	Math.abs(a.x() - b.x()) 
		          + Math.abs(a.x() + a.y() - b.x() - b.y())
		          + Math.abs(a.y() - b.y())) / 2;
	}
}
