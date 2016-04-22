package com.marmar;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import tyckiting.*;

public class JAI {

	private static final JAI INSTANCE=new JAI();
	
	public static JAI instance() {
		return INSTANCE;
	}
	
	
	private Grid grid;
	private GameConfig config;
	private Random r=new Random();
	
	private JAI() {
		System.out.println("New AI");
	}
	
	public void init(GameConfig config) {
		System.out.println("INIT");
		this.config = config;
		grid = new Grid(config.fieldRadius());
	}

	public List<Action> makeDecisions(int roundId, List<Event> events, List<Bot> ownBots, GameConfig config) {
		System.out.println("\n\n\n");
		System.out.println("TURN "+roundId);
		for(Event e:events)
			System.out.println(e);
		System.out.println();
		
		this.config=config;
		Bot[] bots = new Bot[config.bots()*10];
		for(Bot b:ownBots)
			bots[b.botId()]=b;
		
		
		grid.nextTurn(config.move());
		updateGrid(events, bots);
		List<Action> actions=new ArrayList<>();
		
		List<Bot> scanner = new ArrayList<>();
		List<Bot> shooter = new ArrayList<>();
		
		ArrayList<Field> bestEnemyProbs = new ArrayList<>(grid.getBestEnemyProbs());
		double bestEnemyProb=bestEnemyProbs.get(0).getEnemyProb();
		System.out.println("\tBest Enemy Prob: "+bestEnemyProb);
		for(Bot b:ownBots) {
			if(b.alive()) {
				System.out.println("\t"+b.botId()+"\tdodge prob: "+prob(grid.getHitZoneProb(b.pos())));
				if(r.nextDouble()<prob(grid.getHitZoneProb(b.pos()))) {
					actions.add(move(b));
				}
				else {
					if(r.nextDouble()<prob(bestEnemyProb)) {
						shooter.add(b);
					}
					else {
						scanner.add(b);
					}
				}
			}
		}
		
		shoot(actions, shooter, bestEnemyProbs);
		scan(actions, scanner, ownBots, bestEnemyProbs);
		return actions;
	}

	private void scan(List<Action> actions, List<Bot> scanner, List<Bot> ownBots, ArrayList<Field> bestEnemyProbs) {
		LinkedHashSet<Position> candidates = new LinkedHashSet<>(bestEnemyProbs.stream().map(Field::getPos).collect(Collectors.toList()));
		for(Bot b:ownBots) {
			if(b.alive()) {
				candidates.removeAll(GridUtil.iterateRadius(b.pos().x(), b.pos().y(), config.see()));
			}
		}
		for(Bot b:scanner) {
			if(candidates.isEmpty()) {
				System.out.println("\t"+b.botId()+"\tcan't scan anything");
				actions.add(move(b));
			}
			else {
				Position best=candidates.iterator().next();
				System.out.println("\t"+b.botId()+"\tscans "+best);
				candidates.removeAll(GridUtil.iterateRadius(best.x(), best.y(), config.radar()));
				actions.add(Actions.radar(b, best));
			}
		}
	}

	private void shoot(List<Action> actions, List<Bot> shooter, ArrayList<Field> bestEnemyProbs) {
		botsLoop:for(Bot b:shooter) {
			for(Field target:bestEnemyProbs) {
				if(r.nextDouble()<prob(target.getEnemyProb())) {
					actions.add(Actions.cannon(b, target.getPos()));
					System.out.println("\t"+b.botId()+"\tshoots at "+target.getPos()+" with "+prob(target.getEnemyProb()));
					continue botsLoop;
				}
			}
			actions.add(Actions.cannon(b, bestEnemyProbs.get(0).getPos()));
		}
	}

	private MoveAction move(Bot b) {
		int moveRange;
		if(r.nextDouble()<0.333)
			moveRange=config.move();
		else
			moveRange=r.nextInt(config.move()+1);
		List<Field> candidates=new ArrayList<>(grid.getField(b.pos()).getNeighbors(moveRange));
		System.out.println("\t"+b.botId()+"\tmoves "+moveRange);
		//TODO select smarter
		return Actions.move(b, candidates.get(r.nextInt(candidates.size())).getPos());
	}

	private double prob(double prob) {
		return prob/(prob+1);
	}

	private void updateGrid(List<Event> events, Bot[] ownBots) {
		for (Event e : events) {
			//TODO set sight and old scan to zero beforehand
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
