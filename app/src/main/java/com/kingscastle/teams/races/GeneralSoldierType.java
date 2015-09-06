package com.kingscastle.teams.races;


import android.support.annotation.NonNull;

public enum GeneralSoldierType
{
	WORKER ,
	BASIC_HEALER , MEDIUM_HEALER , UPPER_HEALER , ADVANCED_HEALER ,
	BASIC_MELEE_SOLDIER , MEDIUM_MELEE_SOLDIER , UPPER_MELEE_SOLDIER , ADVANCED_MELEE_SOLDIER ,
	BASIC_RANGED_SOLDIER , MEDIUM_RANGED_SOLDIER , UPPER_RANGED_SOLDIER , ADVANCED_RANGED_SOLDIER,
	BASIC_MAGE_SOLDIER, MEDIUM_MAGE_SOLDIER, UPPER_MAGE_SOLDIER, ADVANCED_MAGE_SOLDIER,
	CATAPULT;






	public static boolean isaHealerOrMage(@NonNull GeneralSoldierType gst)
	{
		switch( gst ){
		case ADVANCED_HEALER:
		case ADVANCED_MAGE_SOLDIER:
		case BASIC_HEALER:
		case BASIC_MAGE_SOLDIER:
		case MEDIUM_HEALER:
		case MEDIUM_MAGE_SOLDIER:
		case UPPER_HEALER:
		case UPPER_MAGE_SOLDIER:
			return true;
		default:
			return false;
		}

	}



}

