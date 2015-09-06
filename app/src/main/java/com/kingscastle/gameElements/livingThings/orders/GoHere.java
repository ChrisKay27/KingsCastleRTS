package com.kingscastle.gameElements.livingThings.orders;

import com.kingscastle.framework.Image;
import com.kingscastle.framework.Input.TouchEvent;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.GameElementUtil;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.movement.pathing.Grid;
import com.kingscastle.gameElements.movement.pathing.Path;
import com.kingscastle.gameElements.movement.pathing.PathFinder;
import com.kingscastle.gameElements.movement.pathing.PathFinderParams;
import com.kingscastle.gameElements.movement.pathing.PathFoundListener;
import com.kingscastle.gameUtils.CoordConverter;
import com.kingscastle.gameUtils.vector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class GoHere extends Order
{

	private static final String TAG = "GoHere";

	private static Image iconImage;

	//private static BuildThis buildThis;

	private List<Unit> unitsToBeOrdered = new ArrayList<>();


	private final Grid grid;

	public GoHere( Grid grid ){
		this.grid = grid;
	}
	public static GoHere getInstance(Grid grid)	{
		return new GoHere(grid);
	}


	@Override
	public Image getIconImage()	{
		if( iconImage == null )	{
			//iconImage = Assets.loadImage( R.drawable.go_here );
		}
		return iconImage;
	}



	@Override
	public List<? extends Unit> getUnitsToBeOrdered() {
		return unitsToBeOrdered;
	}



	@Override
	public void setUnitsToBeOrdered(List<? extends Unit> livingThings) {
		unitsToBeOrdered.clear();

		if( livingThings == null )
			return;

        List<Unit> units = new ArrayList<>();
        for( LivingThing lt : livingThings )
            if( lt instanceof Unit )
                units.add((Unit) lt);
        unitsToBeOrdered = units;
	}



	@Override
	public void setUnitToBeOrdered(Unit person)
	{
		unitsToBeOrdered.clear();
		unitsToBeOrdered.add( person );
	}



	private final vector mapRel = new vector();



	@Override
	public boolean analyseTouchEvent(TouchEvent event, CoordConverter cc, CD cd)
	{
		cc.getCoordsScreenToMap( event.x , event.y , mapRel );


		//ArrayList<vector> troopLocs = SquareFormation.getFormationPositions( unitsToBeOrdered , mapRel );
		vector avgLoc = GameElementUtil.getAverageLoc(unitsToBeOrdered);

        final List<Unit> units = new ArrayList<>(unitsToBeOrdered);
        PathFinderParams pfp = new PathFinderParams();
        pfp.fromHere = avgLoc;
        pfp.toHere = new vector(mapRel);
        pfp.grid = grid;
        pfp.mapWidthInPx = cc.getMapWidth();
        pfp.mapHeightInPx = cc.getMapHeight();
        pfp.pathFoundListener = new PathFoundListener() {
            @Override
            public void onPathFound(Path path) {
                path.setHumanOrdered(true);
                if( !path.isEmpty() ) {
                    for( Unit lt : units )
                    {
                        //Log.d( TAG , "Setting path of a " + lt);
                        lt.setPathToFollow( path );
                        lt.setDestination( path.getNext() );
                        lt.setHighThreadTarget( null );
                    }
                }
            }
            @Override
            public void cannotPathToThatLocation(String reason) {
            }
        };

        PathFinder.findPath(pfp);


		return true;
	}



	public static boolean analyseCoordinate( final vector mapRelCoord , final List<? extends Humanoid> thingsToCommand , final CD cd , Grid grid , int mapWidthInPx, int mapHeightInPx)
	{
		try
		{
			List<vector> troopLocs = SquareFormation.getFormationPositions( thingsToCommand , mapRelCoord , grid );
			//Log.d( TAG , "troopLocs.size == " + troopLocs.size() );


			for( final Humanoid lt : thingsToCommand ) //int i = 0 ; i < thingsToCommand.size(); ++i )
			{
                PathFinderParams pfp = new PathFinderParams();
                pfp.fromHere = lt.loc;
                pfp.toHere = troopLocs.remove(troopLocs.size() - 1);
                pfp.grid = grid;
                pfp.mapWidthInPx = mapWidthInPx;
                pfp.mapHeightInPx = mapHeightInPx;
                pfp.pathFoundListener = new PathFoundListener() {
                    @Override
                    public void onPathFound(Path path) {
                        path.setHumanOrdered(true);
                        PathFinder.cleanUpPath(path);
                        path.setIndexOfNextNode(PathFinder.findFarthestNodeWalkable(cd, lt.loc, path));
                        if( !path.isEmpty() ) {
                            //Log.d( TAG , "Setting path of a " + lt);
                            lt.setPathToFollow( path );
                            lt.setDestination( path.getNext() );
                            lt.setHighThreadTarget( null );
                        }
                    }
                    @Override
                    public void cannotPathToThatLocation(String reason) {
                    }
                };

                PathFinder.findPath(pfp);


				//lt.walkToAndStayHere( newPath.getNext() );
			}
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		//}


		//	}.start();

		//SquareFormation.staticMoveTroops( thingsToCommand , mapRelCoord );

		return true;
	}


//	public static boolean analyseCoordinate( final Grid grid , final vector mapRelCoord , LivingThing thingToCommand )	{
//		try
//		{
//			Path path = PathFinder.heyINeedAPath( grid , thingToCommand.loc , mapRelCoord );
//
//			if( path == null || path.getPath() == null || path.getPath().isEmpty() ) {
//				//Log.d( TAG , "Could not find path");
//				return false;
//			}
//
//			path.setHumanOrdered(true);
//			PathFinder.cleanUpPath( path );
//			thingToCommand.setPathToFollow( path );
//		}
//		catch( Exception e )
//		{
//			e.printStackTrace();
//		}
//		return true;
//	}






	protected static void trim( Path path )
	{
		ArrayList<vector> pathToFollow = path.getPath();
		int pathSize = pathToFollow.size();
		if( pathSize > 4 ) {
			pathToFollow.remove(pathToFollow.size()-1);
		}
		if( pathSize > 3 ) {
			pathToFollow.remove(pathToFollow.size()-1);
		}
		if( pathSize > 2 ) {
			pathToFollow.remove(0);
		}
		if( pathSize > 1 ) {
			pathToFollow.remove(0);
		}
	}




	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException
	{
	}


	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.GO_HERE;
	}

	private static final String name = "Move to";

	@Override
	public String toString()
	{
		return name;
	}




}
