package com.kingscastle.gameElements.livingThings.SoldierTypes;


import com.kingscastle.teams.Teams;

public abstract class AdvancedHealer extends Healer
{
	public AdvancedHealer() {
		super();
	}
	public AdvancedHealer(Teams team) {
		super(team);
	}
	{
		buildTime = 5*60*1000;
	}

}
