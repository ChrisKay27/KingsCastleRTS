package com.kingscastle.gameElements.livingThings.SoldierTypes;


import com.kingscastle.teams.Teams;

public abstract class UpperRangedSoldier extends RangedSoldier
{
	public UpperRangedSoldier() {
	}

	public UpperRangedSoldier(Teams team) {
		super(team);
	}

	{
		buildTime = 3*60*1000;
	}

}
