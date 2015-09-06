package com.kingscastle.gameElements.livingThings.SoldierTypes;


import com.kingscastle.teams.Teams;

public abstract class UpperMageSoldier extends MageSoldier{

	public UpperMageSoldier() {

	}
	public UpperMageSoldier(Teams team) {
		super(team);
	}

	{
		buildTime = 3*60*1000;
	}

}
