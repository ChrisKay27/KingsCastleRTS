package com.kingscastle.gameElements.livingThings.buildings;


import android.support.annotation.Nullable;

import com.kingscastle.gameElements.livingThings.LivingThing;

import java.util.ArrayList;
import java.util.Collections;

public class BuildableUnits
{

	private ArrayList<LivingThing> buildableUnits;
	
	
	
	public BuildableUnits( @Nullable LivingThing... units )
	{
		if( units == null || units.length == 0 )
		{
        }
		else
		{		
			buildableUnits = new ArrayList<LivingThing>();
            Collections.addAll(buildableUnits, units);
		}
	}



	public ArrayList<LivingThing> getBuildableUnits() {
		return buildableUnits;
	}



	public void setBuildableUnits(ArrayList<LivingThing> buildableUnits) {
		this.buildableUnits = buildableUnits;
	}
	
	
	

	
}
