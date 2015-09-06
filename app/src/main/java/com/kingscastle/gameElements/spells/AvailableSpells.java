package com.kingscastle.gameElements.spells;

import android.support.annotation.NonNull;

import java.util.ArrayList;



public class AvailableSpells
{
	private final ArrayList<Spell> availableSpells = new ArrayList<Spell>();

	public synchronized boolean addSpell(  @NonNull Spell s )
	{
		for( Spell s2 : availableSpells )
		{
			if( s2.getClass() == s.getClass() )
			{
				return false;
			}
		}

		return availableSpells.add( s );
	}









    @NonNull
    public ArrayList<Spell> getAvailableSpells() {
		return availableSpells;
	}



}
