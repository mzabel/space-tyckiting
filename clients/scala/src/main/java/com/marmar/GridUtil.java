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
	
	public static void main(String[] args) {
		iterateRadius(0, 0, 2);
	}
}
