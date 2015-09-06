package com.kingscastle.gameElements.resources;


import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;


public class TreeSpawner
{
	public enum TreeType
	{
		SMALL , LARGE , SPRUCE
	}


	//private static final String TAG = "TreeSpawner";



	public static boolean spawnTree( MM mm, TreeType treeType , vector loc )
	{
		if( loc == null )
		{
			throw new IllegalArgumentException( "loc == null" ) ;
		}

		//////Log.d(TAG, "TreeType = " + treeType + " , loc = " + loc);

		Tree tree;

		switch(treeType)
		{
		case LARGE: tree = new LargeTree();
		break;

		case SMALL: tree = new SmallTree();
		break;

		default:
		case SPRUCE: tree = new SpruceTree();
		break;
		}

		tree.setLoc( loc );

		boolean added;
		added = mm.add( tree );

		//		if( added )
		//		{
		//			////Log.d(TAG, "Tree Added");
		//			Rpg.getMM().getEm().add( tree.getAnim() , Position.Sorted );
		//		}
		//		else
		//		{
		//			////Log.d(TAG, "Tree not Added");
		//		}

		if( tree.getAnim() == null )
		{
			throw new IllegalArgumentException( "tree.getAnim() == null right after creation." ) ;
		}
		else if( tree.getAnim().isOver() )
		{
			throw new IllegalArgumentException( "tree.getAnim().isOver() right after creation." ) ;
		}

		return added;
	}
}
