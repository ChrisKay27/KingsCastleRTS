package com.kingscastle.gameElements.movement.pathing;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kingscastle.Game;
import com.kingscastle.effects.animations.DarkStarAnim;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.movement.Line;
import com.kingscastle.gameUtils.TimeOutException;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.level.GridUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathFinder
{

	private static final String TAG = "PathFinder";

	private static final ExecutorService pool = Executors.newFixedThreadPool(1);



	/**
	 * Synchronous
	 * @param fromHere
	 * @param toHere
	 * @return
	 * @throws TimeOutException
	 */
	@Nullable
    public static Path heyINeedAPath( Grid grid , vector fromHere , vector toHere , int numberOfTilesAllowedToSearchThrough ) throws TimeOutException
	{
		////Log.d( TAG , "heyINeedAPath( "+ fromHere + " , " + toHere + " )");
		Path path = AStarPathFinder.findMeAPath( grid , fromHere, toHere, System.currentTimeMillis() + 3000 , numberOfTilesAllowedToSearchThrough );

		return path;
	}



	@NonNull
    public static Path reverse( @NonNull Path pathToReverse )
	{

		ArrayList<vector> reversedPath = new ArrayList<vector>();

		for( int i = pathToReverse.size()-1 ; i > -1 ; --i )
			reversedPath.add( pathToReverse.get( i ) );

		Path path = new Path(reversedPath);

		return path;
	}




	public static void cleanUpPath( @Nullable Path path )
	{

		if( path == null || path.size() == 0 || path.getPath() == null || path.getPath().isEmpty() )
		{
			//Log.e( TAG , "path == null || path.size() == 0 || path.getPath() == null || path.getPath().isEmpty()" );
			return;
		}

		ArrayList<vector> nodes = path.getPath();
		ArrayList<vector> removeList = new ArrayList<vector>();


		vector v1 , v2;
		for( int i = 0 ; i < nodes.size()-1 ; )
		{
			int startI = i;
			int nextI = i+1;
			v1 = nodes.get( i );
			v2 = nodes.get( nextI );

			if( v1.x == v2.x )
				while( v1.x == v2.x )
				{
					++nextI;
					if( nextI < nodes.size() )
						v2 = nodes.get( nextI );

					else
					{
						++nextI;
						break;
					}
				}
			else if( v1.y == v2.y )
				while( v1.y == v2.y )
				{
					++nextI;
					if( nextI < nodes.size() )
						v2 = nodes.get( nextI );
					else
					{
						++nextI;
						break;
					}
				}


			if( startI+1 != nextI )
			{
				--nextI;
				for( int j = startI+1 ; j < nextI && j < nodes.size()-1 ; ++j )
					removeList.add( nodes.get( j ) );
			}

			i = nextI;
		}

		nodes.removeAll( removeList );
	}




	public static int findFarthestNodeWalkable( @NonNull CD cd , vector loc , @NonNull Path newPath )
	{
		Line l = new Line( loc , null );
		int i = 0;

		while( i < newPath.size() )
		{
			l.end = newPath.get( i );

			if( cd.checkHitWall( l ) )
				break;

			++i;
		}

		return i > 0 ? i-1 : 0;
	}

	public static void findPath(@NonNull PathFinderParams pfp) {
		findPath( pfp.grid , pfp.fromHere , pfp.toHere , pfp.pathFoundListener , pfp.mapWidthInPx,pfp.mapHeightInPx);
	}

	/**
	 * Async
	 * @param fromHere
	 * @param toHere
	 * @param pathFoundListener
	 */
	public static void findPath( @NonNull final Grid grid , @Nullable final vector fromHere , @Nullable final vector toHere , @Nullable final PathFoundListener pathFoundListener , final int mapWidthInPx,final int mapHeightInPx)
	{
		if( fromHere == null || toHere == null || pathFoundListener == null )
			throw new IllegalArgumentException("fromHere == null || toHere == null || pathFoundListener == null");

		ensureStartAndEndLocsAreWalkable( grid, fromHere , toHere );
		////Log.d( TAG , "Path requested from " + fromHere + " to " + toHere + " completionlistener = " + pathFoundListener );

		pool.execute(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					////Log.d( TAG , "Starting path search");
					//Vector adjustedFromHere = adjustFromHere( fromHere );

					testBounds( fromHere , toHere , mapWidthInPx , mapHeightInPx );
					Path path = getPathBetweenPoints( grid, fromHere , toHere , mapWidthInPx , mapHeightInPx );

					if( path == null )
					{
						pathFoundListener.onPathFound( null );
						return;
					}

					//int indexOfFarthestClearPathNode = findFarthestNodeWalkable( adjustedFromHere , path );

					//indexOfFarthestClearPathNode = indexOfFarthestClearPathNode < path.size()-1 ? indexOfFarthestClearPathNode+1 : indexOfFarthestClearPathNode;

					path.getPath().add( new vector( toHere ) );

					//path.setIndexOfNextNode( indexOfFarthestClearPathNode );

					pathFoundListener.onPathFound( path );


				}
				catch( Exception e )
				{
					e.printStackTrace();
					pathFoundListener.cannotPathToThatLocation(e.getMessage());
				}


			}
		});

	}





	public static Path getPathBetweenPoints( @NonNull Grid grid , @NonNull vector fromHere , @NonNull vector toHere ,int mapWidthInPx ,int mapHeightInPx) throws TimeOutException
	{
		if( ensureStartAndEndLocsAreWalkable( grid, fromHere , toHere ) )
		{
			testBounds( fromHere , toHere , mapWidthInPx , mapHeightInPx );

			Path path = PathFinder.heyINeedAPath( grid , fromHere , toHere, 2000);
			if( path != null )
				path.add( toHere );

			return path;
		}
		return null;
	}






	public static Path tryToGetClearPathBetweenPoints( @NonNull CD cd , vector fromHere , vector toHere )
	{
		ArrayList<GameElement> hitThings = cd.getLineCollisions( new Line( fromHere , toHere ) );


		if( hitThings != null && hitThings.size() <= 2 )
		{
			boolean clearPath = true;

			for( GameElement ge : hitThings )
			{
				if( !(ge.loc == fromHere || ge.loc == toHere) )
				{
					clearPath = false;
					break;
				}
			}

			if( clearPath )
			{
				ArrayList<vector> nodes = new ArrayList<vector>();
				nodes.add( toHere );
				return new Path(nodes);
			}
		}

		return null;
	}




	private static void testBounds(@NonNull vector fromHere, @NonNull vector toHere, int mapWidthInPx, int mapHeightInPx)
	{
		if( fromHere.x < 0 || fromHere.y < 0 || fromHere.x >= mapWidthInPx || fromHere.y >= mapHeightInPx ||
				toHere.x < 0 || toHere.y < 0 || toHere.x >= mapWidthInPx || toHere.y >= mapHeightInPx )
		{
			throw new IllegalArgumentException("Trying to path out of bounds");
		}
	}


	private static boolean ensureStartAndEndLocsAreWalkable( @NonNull Grid grid , @NonNull vector start , @NonNull vector end)
	{
		boolean[][] gridTiles = grid.getGridTiles();
		final float gridSize = grid.getGridSize();

		int startI = (int)(start.x/gridSize);
		int startJ = (int)(start.y/gridSize);

		int endI = (int)(end.x/gridSize);
		int endJ = (int)(end.y/gridSize);

		if( startI >= gridTiles.length || startJ >= gridTiles[0].length || startI < 0 || startJ < 0
				|| endI >= gridTiles.length || endJ >= gridTiles[0].length || endI < 0 || endJ < 0 ) {
			Log.e(TAG, "TRYING TO PATH OUT OF BOUNDS!!!\n" + Arrays.asList(new Throwable().getStackTrace()));
			return false;
		}
		if( !gridTiles[startI][startJ] ){
			//Log.e( TAG , "Trying to path to unwalkable tile(startI=" +startI+" , startJ=" + startJ + ")");
			return false;
		}
		//throw new IllegalArgumentException("Trying to path to unwalkable tile(startI=" +startI+" , startJ=" + startJ + ")");
		if( !gridTiles[endI][endJ] ){
			//Log.e( TAG , "Trying to path to unwalkable tile(endI=" +endI+" , endJ=" + endJ + ")");
			return false;
		}

		//throw new IllegalArgumentException("Trying to path to unwalkable tile(endI=" +endI+" , endJ=" + endJ + ")");
		return true;
	}



	@NonNull
    protected static vector adjustFromHere( @NonNull Grid grid , @NonNull vector fromHere )
	{
		boolean[][] gridTiles = grid.getGridTiles();


		if( gridTiles[ (int)(fromHere.x/grid.getGridSize()) ][ (int)(fromHere.y/grid.getGridSize()) ] )
			return fromHere;



		float dx = fromHere.x%grid.getGridSize();
		float dy = fromHere.y%grid.getGridSize();

		float gridSizeDiv2 = grid.getGridSize()/2;



		vector v = new vector( fromHere );

		if( dx > gridSizeDiv2 )
			v.add( grid.getGridSize() - dx , 0 );
		else
			v.add( -dx , 0 );

		if( dy > gridSizeDiv2 )
			v.add( 0 , grid.getGridSize() - dy  );
		else
			v.add( 0 , -dy );


		return v;
	}





	public static void findPathTo(@NonNull vector loc, @NonNull RectF area, @NonNull GridUtil gUtil,
			@NonNull PathFoundListener pathFoundListener , int mapWidthInPx, int mapHeightInPx ) {
		vector jobLocTemp = null;
		vector mLocTemp = null;
		try
		{
			jobLocTemp = gUtil.getWalkableLocNextToThis( loc , area );
			mLocTemp = gUtil.getClosestAdjWalkableTile( loc );
		}
		catch( Exception e )
		{
			if( Game.testingVersion ){

				DarkStarAnim dsa = new DarkStarAnim( new vector( area.centerX() , area.centerY() ));
				dsa.setLooping( true );
				dsa.setAliveTime( 30000 );

//				if( mm != null )
//					mm.getEm().add( dsa );
				e.printStackTrace();
			}

			return;
		}
		if( jobLocTemp == null || mLocTemp == null ){
			pathFoundListener.cannotPathToThatLocation("jobLocTemp == null || mLocTemp == null");
			return;
		}
		final vector jobLoc = jobLocTemp;
		final vector mLoc = mLocTemp;

		findPath( gUtil.getGrid() , mLoc , jobLoc , pathFoundListener ,  mapWidthInPx,  mapHeightInPx);
	}



}//end class









