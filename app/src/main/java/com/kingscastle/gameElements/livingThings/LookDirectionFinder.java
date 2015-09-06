package com.kingscastle.gameElements.livingThings;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

public class LookDirectionFinder
{

	public static Rpg.Direction getDir( @NonNull vector from , @NonNull vector towards )
	{		
		return vector.getDirection4(new vector(from, towards).turnIntoUnitVector());
	}

}
