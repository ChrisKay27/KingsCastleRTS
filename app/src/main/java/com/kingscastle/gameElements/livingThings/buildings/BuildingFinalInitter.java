package com.kingscastle.gameElements.livingThings.buildings;


import android.support.annotation.NonNull;

import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.level.PR;
import com.kingscastle.teams.Team;

public class BuildingFinalInitter
{


	public static boolean finalInit( @NonNull Building b , @NonNull MM mm )
	{
		try
		{
			Team team = mm.getTM().getTeam( b.getTeamName() );
			if( team == null ) return false;

			PR pr = team.getPlayer().getPR();

//			BuildQueue buildQueue = b.getBuildQueue();
//
//			if( buildQueue != null )
//			{
//				ArrayList<Queueable> theQueued = buildQueue.getQueued();
//
//				synchronized( theQueued ){
//					for( Queueable q : theQueued )
//						if( q instanceof LivingThing)
//							pr.incPopCurr( ((LivingThing) q ).getCosts().getPopCost() );
//						else if( q instanceof LevelUpTechnology)
//							team.onTechResearchStarted(b,(LevelUpTechnology) q);
//				}
//
//
//				Queueable q = buildQueue.getCurrentlyBuilding();
//				if( q instanceof LivingThing )
//					pr.incPopCurr( ((LivingThing) q ).getCosts().getPopCost() );
//				else if( q instanceof LevelUpTechnology )
//					team.onTechResearchStarted(b,(LevelUpTechnology) q);
//
//			}


			b.create(mm);
			return true;
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		return false;
	}








}
