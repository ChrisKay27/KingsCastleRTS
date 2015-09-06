package com.kingscastle.gameElements.livingThings.SoldierTypes;


import com.kingscastle.teams.Teams;

public abstract class AdvancedMeleeSoldier extends MeleeSoldier
{
	public AdvancedMeleeSoldier() {
		super();
	}
	public AdvancedMeleeSoldier(Teams team) {
		super(team);
	}
	{
		buildTime = 5*60*1000;
	}

}
