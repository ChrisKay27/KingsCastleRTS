package com.kingscastle.gameElements.livingThings.orders;


import com.kingscastle.gameElements.livingThings.LivingThing;

public abstract class Formation {
	
	public enum Formations{
		ProtectionFormation,GuardLeader
	}

	public abstract boolean checkInFormation(LivingThing lt);

	public abstract void organizeTroops(LivingThing leader);

	public abstract void addToFormation(LivingThing lt);


	public abstract void reorganizeTroops();
	
	public abstract void clearPositions();




}
