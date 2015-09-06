package com.kingscastle.gameUtils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.teams.Team;
import com.kingscastle.teams.Teams;
import com.kingscastle.ui.UI;

import java.util.ArrayList;



public class BuildQueueChecker
{

	/**
	 * 
	 * @param buildQueue
	 * @param building
	 * @param mm
	 * @return
	 */
	public static boolean checkBuildQueue( @NonNull BuildQueue buildQueue , Building building , @NonNull MM mm )
	{
		ArrayList<LivingThing> lts=null;
		while( buildQueue.isThereACompletedQueueable() ){
			Queueable builtOrResearched = buildQueue.getCompletedQueueable();

			if( builtOrResearched != null)
			{
				if( builtOrResearched instanceof LivingThing)
				{
					LivingThing unit = (LivingThing) builtOrResearched;


					Team t = mm.getTeam( unit.getTeamName() );
					if( t != null )
						t.onUnitCreated( unit );

					if( lts == null )
						lts = new ArrayList<LivingThing>();
					lts.add( unit );




						//						Path path = building.getPathToDeployLocation(mm.getLevel().getGrid());
						//						if( path != null )
						//							unit.setPathToFollow( new Path(path) );
						//
						//						else
						//							unit.setDestination( building.getDeployLoc() );




				}


				//				boolean anotherInLine = buildQueue.startBuildingFirst();
				//				if( !anotherInLine )
				//				{
				//					if( building.getTeamName() == Teams.BLUE )
				//					{
				//						building.getBuildingAnim().remove( buildQueue.getProgressBar() );
				//						buildQueue.getProgressBar().setShowCurrentAndMax( false );
				//					}
				//				}

			}
		}


		return buildQueue.currentlyBuilding != null;
	}



	public static boolean addToBuildQueue( @NonNull BuildQueue buildQueue , @Nullable Queueable toBeBuild , @NonNull Building building )
	{
		if ( toBeBuild == null )
			throw new IllegalArgumentException(" toBeBuild == null " );

		buildQueue.add( toBeBuild );

		if( buildQueue.startBuildingFirst() )
		{
			if( building.getTeamName() == Teams.BLUE )
			{
				building.getBuildingAnim().add( buildQueue.getProgressBar() , true );

				UI.get().refreshSelectedUI();
				//				if ( building.isSelected() ){
				//					QueueDisplay.displayQueue( Rpg.getGame() , buildQueue );
				//					RushBuilding.showFinishNowButtonIfNeededTSafe(building);
				//				}

				return true;
			}
			return true;
		}

		return true;
	}




}
