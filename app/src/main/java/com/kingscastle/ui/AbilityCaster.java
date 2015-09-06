package com.kingscastle.ui;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Input;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.teams.Teams;

public class AbilityCaster
{
	//private static final String TAG = "UnitOrders";

	private static final AbilityCaster abilityCaster = new AbilityCaster();

	@Nullable
    private Ability pendingAbility;


	private AbilityCaster()
	{
	}


	@NonNull
    public static AbilityCaster getInstance()
	{
		return abilityCaster;
	}







	public boolean giveOrders( @NonNull Input.TouchEvent event )
	{
		if( pendingAbility == null ){
			return false;
		}

		try
		{
			if ( pendingAbility.analyseTouchEvent( event ) )
			{
				pendingAbility = null;
				return true;
			}
		}
		catch( Exception e )
		{
			pendingAbility = null;
		}
		return false;
	}



	public void setPendingAbility( @NonNull Ability ab )
	{
		pendingAbility = ab;
		ab.setTeam( Teams.BLUE );
	}








	public boolean isThereAPendingOrder(){
		return pendingAbility != null;
	}


	public void cancel(){
		pendingAbility = null;
	}


	@Nullable
    public Ability getPendingAbility(){
		return pendingAbility;
	}


	public void clearOrder(){
		pendingAbility = null;
	}




}
