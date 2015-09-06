package com.kingscastle.gameElements.resources;


import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;


public class MineSpawner
{
	public enum MineType
	{
		GOLD , ORE
    }


	//private static final String TAG = "MineSpawner";



	public static boolean spawnMine(MM mm, MineType mineType , vector loc )
	{
		if( loc == null )
		{
			throw new IllegalArgumentException( "loc == null" ) ;
		}

		//////Log.d(TAG, "MineType = " + mineType + " , loc = " + loc);

		Mine mine;

		switch(mineType)
		{
		case GOLD: mine = new GoldMine();
		break;
		default:
		case ORE: mine = new OreMine();
		break;

		}

		mine.setLoc(loc);

		boolean added;

		added = mm.add( mine );

		//		if( added )
		//		{
		//			////Log.d(TAG, "Mine Added");
		//			Rpg.getMM().getEm().add( mine.getAnim() , Position.Sorted );
		//		}
		//		else
		//		{
		//			////Log.d(TAG, "Mine not Added");
		//		}

		if( mine.getAnim() == null )
		{
			throw new IllegalArgumentException( "tree.getAnim() == null right after creation." ) ;
		}
		else if( mine.getAnim().isOver() )
		{
			throw new IllegalArgumentException( "tree.getAnim().isOver() right after creation." ) ;
		}

		return added;
	}
}
