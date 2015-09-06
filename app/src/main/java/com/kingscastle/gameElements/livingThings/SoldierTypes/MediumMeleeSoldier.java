package com.kingscastle.gameElements.livingThings.SoldierTypes;


import com.kingscastle.teams.Teams;

public abstract class MediumMeleeSoldier extends MeleeSoldier
{

	public MediumMeleeSoldier() {

	}
	public MediumMeleeSoldier(Teams team) {
		super(team);
	}
	{
		buildTime = 60*1000;
	}

}
