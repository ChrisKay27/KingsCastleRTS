package com.kingscastle.gameElements.targeting;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kingscastle.framework.Settings;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.TargetingParams;
import com.kingscastle.gameElements.livingThings.army.ArmyManager;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.livingThings.buildings.BuildingManager;
import com.kingscastle.gameElements.managment.ListPkg;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Team;
import com.kingscastle.teams.TeamManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TargetFinder
{
	private static final String TAG = "TargetFinder";

	public enum CondRespon
	{
		RETURN_THIS_NOW , TRUE , FALSE , DEFAULT
	}

	private final ExecutorService pool = Executors.newFixedThreadPool(1);

	private final TeamManager tm;

	public TargetFinder(TeamManager tm){
		this.tm = tm;
	}

	public void findTargetAsync( @NonNull final TargetingParams params , @NonNull final LivingThing attacker )
	{
		pool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					//					Log.v("TargetFinder", "Looking for a target for a " + attacker );
					LivingThing lt = findTarget(params);
					//				if( attacker.getTeamName() == Teams.RED && attacker instanceof TownCenter )
					//					Log.v("TargetFinder", "Found a " + lt );
					if (lt != null) {
						if (attacker.getTarget() == null)
							attacker.setTarget(lt);
					}
				}
				catch(Exception e){
					Log.e(TAG,"Exception finding target for " + attacker,e);
				}
			}
		});
	}


	@Nullable
    public LivingThing findTarget( @NonNull TargetingParams params )
	{
		if( Settings.targetFindingDisabled )
			return null;

		boolean lookAtSoldiers = params.lookAtSoldiers;
		boolean lookAtBuildings = params.lookAtBuildings;
		boolean lookAtBuildingsFirst = params.lookAtBuildingsFirst;

		float x = params.getFromThisPoint().x;
		float y = params.getFromThisPoint().y;


		for ( Team t : tm.getTeams() )
		{
			if( params.onThisTeam && t.getTeamName() != params.team )
				continue;

			if( !params.onThisTeam && t.getTeamName() == params.team )
				continue;


			if( lookAtBuildingsFirst && lookAtBuildings ){
				LivingThing target = findOneWithRange( t.getBm() , params );
				if( target != null )
					return target;
			}

			LivingThing soldierTarget = null;
			if( lookAtSoldiers )
			{
				ArmyManager.CollisionPartitions cp = t.getAm().getCollisionPartitions();
				if( cp == null )
					continue;

				LivingThing[] lts = cp.getPartition(x, y);
				int size = cp.getSizeForPartition(x, y);

				soldierTarget = findOneWithRange( lts , size , params );
				if( params.soldierPriority )
					if( soldierTarget != null )
						return soldierTarget;
			}

			if( !lookAtBuildingsFirst && lookAtBuildings )
			{
				LivingThing target = findOneWithRange( t.getBm() , params );
				if( target != null ){
					if( soldierTarget != null ){
						if( target.loc.distanceSquared(x,y) < soldierTarget.loc.distanceSquared(x,y))
							return target;
						else
							return soldierTarget;
					}
					else
						return target;
				}
				if( soldierTarget != null )
					return soldierTarget;
			}

		}

		return null;
	}


	private static LivingThing findOneWithRange( LivingThing[] lts , int size , @NonNull TargetingParams params )
	{
		ArrayList<LivingThing> targets = new ArrayList<LivingThing>();

		vector loc = params.fromThisPoint;
		float rangeSquared = params.withinRangeSquared;

		for( int i = 0 ; i < size ; ++i )
		{
			LivingThing lt = lts[i];

			if( lt.dead )
				continue;

			if( loc.distanceSquared( lt.loc ) < rangeSquared )
				targets.add( lt );
			//			if( targets.size() > 20 )
			//				break;
		}

		while( !targets.isEmpty() ){
			LivingThing closest = getClosest( loc , targets );
			switch( params.postRangeCheckCondition( closest ) )
			{
			default:
			case FALSE:
				targets.remove( closest );
				continue;
			case RETURN_THIS_NOW:
				return closest;
			case DEFAULT:
			case TRUE:
				break;
			}

			return closest;
		}

		return null;
	}



	@Nullable
    private static LivingThing getClosest(@NonNull vector loc,
			@NonNull ArrayList<LivingThing> targets) {

		float closestDist = Float.MAX_VALUE;
		LivingThing closest=null;

		for( LivingThing lt : targets ){
			float dist = loc.distanceSquared(lt.loc);
			if( dist < closestDist ){
				closest = lt;
				closestDist = dist;
			}
		}
		return closest;
	}



	private static LivingThing findOneWithRange( @NonNull BuildingManager bm , @NonNull TargetingParams params )
	{

		vector loc = params.fromThisPoint;
		float rangeSquared = params.withinRangeSquared;

		ListPkg<Building> bPkg = bm.getBuildings();


		LivingThing[] pTargets = params.possibleTargets;
		int size;
		synchronized( bPkg )
		{
			Building[] buildings = bPkg.list;
			//int size2 = bPkg.size;
			size = bPkg.size;
			System.arraycopy(buildings, 0, pTargets, 0, size);
		}


		for( int i = 0 ; i < size ; ++i )
		{
			LivingThing b = pTargets[i];

			if( b.dead )
				continue;

			if( loc.distanceSquared( b.loc ) < rangeSquared )
			{
				switch( params.postRangeCheckCondition( b ) )
				{
				default:
				case FALSE:
					continue;
				case RETURN_THIS_NOW:
					return b;
				case DEFAULT:
				case TRUE:
					break;
				}

				return b;
			}
		}
		//	}


		return null;
	}

}
