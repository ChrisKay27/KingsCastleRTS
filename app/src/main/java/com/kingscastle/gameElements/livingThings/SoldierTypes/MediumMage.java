package com.kingscastle.gameElements.livingThings.SoldierTypes;


import com.kingscastle.teams.Teams;

public abstract class MediumMage extends MageSoldier
{

	public MediumMage() {
	}

	public MediumMage(Teams team) {
		super(team);
	}

	{
		buildTime = 60*1000;
	}

}
