package com.marmar;

import tyckiting.Bot;
import tyckiting.CannonAction;
import tyckiting.MoveAction;
import tyckiting.Position;
import tyckiting.RadarAction;

public class Actions {
	public static MoveAction move(Bot bot, Position pos) {
		return new MoveAction(bot.botId(), pos, "move");
	}
	
	public static RadarAction radar(Bot bot, Position pos) {
		return new RadarAction(bot.botId(), pos, "radar");
	}
	
	public static CannonAction cannon(Bot bot, Position pos) {
		return new CannonAction(bot.botId(), pos, "cannon");
	}
}
