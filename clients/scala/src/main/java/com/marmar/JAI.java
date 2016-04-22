package com.marmar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tyckiting.*;

public class JAI {

	private Grid grid;
	private GameConfig config;
	private Random r=new Random();

	public void init(GameConfig config) {
		this.config = config;
		grid = new Grid(config.fieldRadius());
	}

	public List<Action> makeDecisions(int roundId, List<Event> events, List<Bot> ownBots, GameConfig config) {
		this.config=config;
		Bot[] bots = new Bot[config.bots()];
		for(Bot b:ownBots)
			bots[b.botId()]=b;
		
		
		grid.nextTurn();
		updateGrid(events, bots);
		List<Action> actions=new ArrayList<>();
		
		List<Bot> scanner = new ArrayList<>();
		List<Bot> shooter = new ArrayList<>();
		
		for(Bot b:ownBots) {
			if(b.alive()) {
				if(r.nextDouble()<prob(grid.getHitZoneProb(b.pos())))
					actions.add(move(b));
				else {
					if(r.nextDouble()<prob(grid.getBestHitZoneProb()))
						shooter.add(b);
					else
						scanner.add(b);
				}
			}
		}
		
		shoot(actions, shooter);
		scan(actions, scanner);
	}

	private void scan(List<Action> actions, List<Bot> scanner) {
		// TODO Auto-generated method stub
		
	}

	private void shoot(List<Action> actions, List<Bot> shooter) {
		// TODO Auto-generated method stub
		
	}

	private MoveAction move(Bot b) {
		// TODO Auto-generated method stub
		return null;
	}

	private double prob(double prob) {
		return prob/(prob+1);
	}

	private void updateGrid(List<Event> events, Bot[] ownBots) {
		for (Event e : events) {
			/*if(e instanceof HitEvent) {
				if(ownBots[((HitEvent) e).botId()]!=null)
					grid.addHitZoneProb(ownBots[((HitEvent) e).botId()].pos());
				//TODO do something if we hit an enemy
			}
			else */if(e instanceof DamagedEvent) {
				grid.addHitZoneProb(ownBots[((DamagedEvent) e).botId()].pos(),4);
			}
			else if(e instanceof DieEvent) {
				//don't know yet
			} 
			else if(e instanceof SeeEvent) {
				grid.addEnemyProb(((SeeEvent) e).pos(),4);
			} 
			else if(e instanceof RadarEchoEvent) {
				grid.addEnemyProb(((RadarEchoEvent) e).pos(),4);
			} 
			else if(e instanceof DetectedEvent) {
				grid.addHitZoneProb(ownBots[((DetectedEvent) e).botId()].pos(),4);
			} 
			else if(e instanceof MoveEvent) {
				//nothing useful
			} 
			else if(e instanceof SeeAsteroidEvent) {
				//ööööööööö
			} 
			else if(e instanceof UnknownEvent) {
				System.err.println("Unknown Event "+e);
			}
		}
	}
}
