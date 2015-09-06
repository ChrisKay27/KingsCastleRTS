package com.kingscastle.ui;


import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import org.jetbrains.annotations.NotNull;

public class EnemyAtLocationChecker
{


	/**
	 * 
	 * @param mapRelCoord
	 * @param teamOfInterest Team to either ignore or look on based on onThatTeam paramater.
	 * @param onThatTeam If true it looks only on that team, used for finding players to heal on your own team. If false only looks at every other team.
	 * @return null if nothing is found.
	 */
	public static LivingThing findEnemyHere( @NotNull CD cd, @NotNull vector mapRelCoord , @NotNull Teams teamOfInterest , boolean onThatTeam )
	{

		GameElement possibleTarget = cd.checkPlaceableOrTarget( mapRelCoord ) ;

		if( possibleTarget instanceof LivingThing )
		{
			LivingThing lt = (LivingThing) possibleTarget;

			if ( lt.getTeamName() == teamOfInterest )
			{
				if( onThatTeam ) {
					return lt;
				} else {
					return null;
				}
			}
			else
			{
				if( !onThatTeam ) {
					return lt;
				} else {
					return null;
				}
			}
		}
		//
		//		for ( Team team : ManagerManager.getInstance().getTeamManager().getTeams() )
		//		{
		//
		//			if ( team.getTeamName() == teamOfInterest && !onThatTeam )
		//			{
		//				continue;
		//			}
		//			if( onThatTeam && team.getTeamName() != teamOfInterest ) // looks ON onAllTeamsButThis
		//			{
		//				continue;
		//			}
		//
		//			for ( LivingThing lt : team.getAm().getArmy() )
		//			{
		//				if( lt.area.contains( mapRelCoord.getIntX() ,mapRelCoord.getIntY() ))
		//				{
		//					return lt;
		//				}
		//			}
		//
		//			for ( LivingThing b : team.getBm().getBuildings() )
		//			{
		//				if( b.area.contains( mapRelCoord.getIntX() ,mapRelCoord.getIntY() ))
		//				{
		//					return b;
		//				}
		//			}
		//
		//		}



		return null;
	}

}
