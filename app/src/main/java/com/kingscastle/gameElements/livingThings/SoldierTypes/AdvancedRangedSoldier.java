package com.kingscastle.gameElements.livingThings.SoldierTypes;


import com.kingscastle.teams.Teams;

public abstract class AdvancedRangedSoldier extends RangedSoldier
{
	public AdvancedRangedSoldier() {
		super();
	}
	public AdvancedRangedSoldier(Teams team) {
		super(team);
	}

	{
		buildTime = 5*60*1000;
	}

}
