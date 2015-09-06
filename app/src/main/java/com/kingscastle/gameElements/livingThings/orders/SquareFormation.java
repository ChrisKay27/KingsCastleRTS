package com.kingscastle.gameElements.livingThings.orders;

import com.kingscastle.Game;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.Rpg.Direction;
import com.kingscastle.gameElements.GameElementUtil;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Healer;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MageSoldier;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MeleeSoldier;
import com.kingscastle.gameElements.livingThings.SoldierTypes.RangedSoldier;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.livingThings.army.Warrior;
import com.kingscastle.gameElements.movement.pathing.Grid;
import com.kingscastle.gameUtils.vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class SquareFormation extends Formation {
	private static final String TAG = "SquareFormation";

	private static final float unitSize = Rpg.getNormalPerceivedArea().width() + 5*Rpg.getDp() ;


	public void moveTroops( List< ? extends Humanoid> units , vector dest )
	{
		staticMoveTroops(units,dest) ;
	}

	public static boolean staticMoveTroops( List < ? extends Humanoid> units , vector dest )
	{
		if ( units == null || dest == null )
			throw new IllegalArgumentException( "units = " + units + " dest = " + dest ) ;

		if( units.isEmpty() )
			return false;


		if( units.size() == 1 )	{
			units.get( 0 ).walkToAndStayHere( dest );
			return true;
		}
		else if( units.size() == 2 ){
			units.get( 0 ).walkToAndStayHere( dest );
			vector dest2 = new vector();
			dest2.set( dest );
			dest2.add( units.get( 1 ).area.width() , 0 );
			units.get( 1 ).walkToAndStayHere(  dest2 );
			return true;
		}




		List<Humanoid> meleeSoldiers = new ArrayList<>() ;
		List<Humanoid> rangedSoldiers = new ArrayList<>() ;

		for(Humanoid u : units)
		{
			if( u instanceof MeleeSoldier)			
				meleeSoldiers.add( u ) ;				
			
			else if( u instanceof RangedSoldier)			
				rangedSoldiers.add( u ) ;				
			
			else if( u instanceof MageSoldier)			
				rangedSoldiers.add( u ) ;
							
			else if( u instanceof Healer)			
				rangedSoldiers.add( u ) ;
				
			else if ( u instanceof Unit)			
				rangedSoldiers.add( u ) ;
							
			else			
				rangedSoldiers.add( u ) ;	
			            
		}
        

		float unitSize = units.get(0).getPerceivedArea().width() + Rpg.fiveDp ;

		vector origLoc = GameElementUtil.getAverageLoc(units) ;
		Direction dirToDest = vector.getDirection4( new vector( origLoc , dest ).getUnitVector()) ;

		//int unitSize = Rpg.getNormalPerceivedArea().width() + 5*Rpg.getDp() ;

		int numMeleeUnits  = meleeSoldiers.size() ;
		int meleeUnitRows  = numMeleeUnits/NUM_SOLDIERS_PER_ROW;
		meleeUnitRows += numMeleeUnits%NUM_SOLDIERS_PER_ROW != 0 ? 1 : 0;

		int numRangedUnits = rangedSoldiers.size() ;
		int rangedUnitRows = numRangedUnits/NUM_SOLDIERS_PER_ROW ;
		rangedUnitRows += ( numRangedUnits%NUM_SOLDIERS_PER_ROW != 0 ? 1 : 0 );

		//		int numMeleeUnits = meleeSoldiers.size() ;
		//		int meleeUnitRows = numMeleeUnits/7 + 1 ;
		//		int numRangedUnits = rangedSoldiers.size() ;
		//		int rangedUnitRows = numRangedUnits/7 + 1 ;

		vector unitsDest ;
		int index = 0 ;

		switch(dirToDest)
		{
		default:
		case W :
			for(int i = 0  ;  i  <  meleeUnitRows  ;  ++i )
			{
				for( int j = 0  ;  j  <  NUM_SOLDIERS_PER_ROW  ;  ++j )
				{
					if( index == meleeSoldiers.size()) {
						break ;
					}
					unitsDest = new vector(dest) ;
					unitsDest.translate( - i * unitSize , -unitSize * 3 + j * unitSize ) ;
					meleeSoldiers.get( index ).walkToAndStayHere( unitsDest , true ) ;
					index++ ;
				}
			}
			index = 0 ;

			for( int i = 0  ;  i  <  rangedUnitRows  ;  ++i )
			{
				for( int j = 0  ;  j  <  NUM_SOLDIERS_PER_ROW  ;  ++j )
				{
					if( index == rangedSoldiers.size() )
					{
						break ;
					}
					unitsDest = new vector(dest) ;
					unitsDest.translate( unitSize + i*unitSize ,-unitSize*3 + j*unitSize ) ;
                    ((Unit)rangedSoldiers.get(index)).walkToAndStayHere(unitsDest,true) ;
					index++ ;
				}
			}
			break ;

		case E :
			for( int i = 0  ;  i  <  meleeUnitRows  ;  ++i )
			{
				for( int j = 0  ;  j  <  NUM_SOLDIERS_PER_ROW  ;  ++j )
				{
					if( meleeSoldiers.size() == 0 )
					{
						break ;
					}
					unitsDest = new vector(dest) ;
					unitsDest.translate( i*unitSize , -unitSize*3 + j*unitSize ) ;
					meleeSoldiers.remove(0).walkToAndStayHere(unitsDest,true) ;
				}
			}
			index = 0 ;
			for(int i = 0 ; i < rangedUnitRows ;  ++i )
			{
				for(int j=0 ; j < NUM_SOLDIERS_PER_ROW  ;  ++j )
				{
					if( rangedSoldiers.size() == 0 )
					{
						break ;
					}
					unitsDest = new vector( dest ) ;
					unitsDest.translate(-unitSize - i*unitSize,-unitSize*3 + j*unitSize ) ;
					rangedSoldiers.remove(0).walkToAndStayHere( unitsDest , true ) ;
				}
			} break ;

		case N :
			for(int i = 0 ; i < meleeUnitRows ;  ++i )
			{
				for(int j=0 ; j < NUM_SOLDIERS_PER_ROW ;  ++j )
				{
					if(meleeSoldiers.size()==0)
					{
						break ;
					}
					unitsDest = new vector(dest) ;
					unitsDest.translate(-unitSize*3 + j*unitSize, -meleeUnitRows*unitSize + i*unitSize) ;
					meleeSoldiers.remove(0).walkToAndStayHere(unitsDest,true) ;
				}
			}
			index = 0 ;
			for(int i = 0 ; i < rangedUnitRows ;  ++i )
			{
				for(int j=0 ; j < NUM_SOLDIERS_PER_ROW ;  ++j ){
					if(rangedSoldiers.size()==0)
					{
						break ;
					}
					unitsDest = new vector(dest) ;
					unitsDest.translate(-unitSize*3 + j*unitSize,i*unitSize) ;
					rangedSoldiers.remove(0).walkToAndStayHere(unitsDest,true) ;
				}
			} break ;
		case S :
			for(int i = 0 ; i < meleeUnitRows ;  ++i )
			{
				for(int j=0 ; j < NUM_SOLDIERS_PER_ROW ;  ++j )
				{
					if(meleeSoldiers.size()==0)
					{
						break ;
					}
					unitsDest = new vector(dest) ;
					unitsDest.translate(-unitSize*3 + j*unitSize,i*unitSize) ;
					meleeSoldiers.remove(0).walkToAndStayHere(unitsDest,true) ;
				}
			}
			index = 0 ;
			for(int i = 0  ;  i < rangedUnitRows ;  ++i )
			{
				for(int j = 0 ; j < NUM_SOLDIERS_PER_ROW ;  ++j )
				{
					if(rangedSoldiers.size()==0)
					{
						break ;
					}
					unitsDest = new vector(dest) ;
					unitsDest.translate(-unitSize*3 + j*unitSize, -rangedUnitRows*unitSize + i*unitSize) ;

					rangedSoldiers.remove(0).walkToAndStayHere(unitsDest,true) ;
				}
			} break ;

		}
		return true;
	}


	private static final int NUM_SOLDIERS_PER_ROW = 6;

	public static List<vector> getFormationPositions( List < ? extends LivingThing> units , vector dest , Grid grid ) throws TimeoutException
	{
		if ( units == null || dest == null )
			throw new IllegalArgumentException( "units = " + units + " dest = " + dest ) ;

		long timeOutAt = System.currentTimeMillis() + 1500;

		List<vector> locations = new ArrayList<>();


		int numSoldiers = units.size();

		if( numSoldiers == 1 )
		{
			locations.add( dest );
			return locations;
		}
		else if( numSoldiers == 2 )
		{
			locations.add(dest);

			vector dest2 = new vector();
			dest2.set( dest );
			dest2.add( units.get( 1 ).area.width() , 0 );

			locations.add( dest2 );
			return locations;
		}


		List<LivingThing> meleeSoldiers = new ArrayList<>() ;
		List<LivingThing> rangedSoldiers = new ArrayList<>() ;

		for( LivingThing u : units )
		{
			if( timeOutAt < System.currentTimeMillis() )
			{
				if( Game.testingVersion )
					//Log.e( TAG , "TIMEOUT EXCEPTION!");
					return locations;
			}
			if( u instanceof MeleeSoldier )
			{
				meleeSoldiers.add( u ) ;
				
			}
			else if( u instanceof RangedSoldier )
			{
				rangedSoldiers.add( u ) ;
				
			}
			else if( u instanceof MageSoldier )
			{
				rangedSoldiers.add( u ) ;
				
			}
			else if( u instanceof Healer )
			{
				rangedSoldiers.add( u ) ;
				
			}
			else if ( u instanceof Unit )
			{
				rangedSoldiers.add( u ) ;
				
			}
			else
			{
				rangedSoldiers.add( u ) ;
				
			}
		}

		vector origLoc = GameElementUtil.getAverageLoc ( units ) ;
		Direction dirToDest = vector.getDirection4( new vector( origLoc , dest ).getUnitVector()) ;

		//int unitSize = Rpg.getNormalPerceivedArea().width() + 5*Rpg.getDp() ;


		int numSoldiersPerRow = NUM_SOLDIERS_PER_ROW;
		if( numSoldiers > 40 )
			numSoldiersPerRow = 8;

		int numMeleeUnits  = meleeSoldiers.size() ;


		////Log.d( TAG , "numMeleeUnits = " + numMeleeUnits );
		int meleeUnitRows  = numMeleeUnits/numSoldiersPerRow;// + numMeleeUnits%NUM_SOLDIERS_PER_ROW != 0 ? 1 : 0 ;
		////Log.d( TAG , "numMeleeUnits/NUM_SOLDIERS_PER_ROW = " + numMeleeUnits/NUM_SOLDIERS_PER_ROW );
		meleeUnitRows += numMeleeUnits%numSoldiersPerRow != 0 ? 1 : 0;
		////Log.d( TAG , "numMeleeUnits/NUM_SOLDIERS_PER_ROW + numMeleeUnits%NUM_SOLDIERS_PER_ROW != 0 ? 1 : 0 " + meleeUnitRows );
		////Log.d( TAG , "numMeleeUnits%NUM_SOLDIERS_PER_ROW = " + numMeleeUnits%NUM_SOLDIERS_PER_ROW );


		int numRangedUnits = rangedSoldiers.size() ;
		////Log.d( TAG , "numRangedUnits = " + numRangedUnits );
		int rangedUnitRows = numRangedUnits/numSoldiersPerRow ;
		////Log.d( TAG , "numRangedUnits/NUM_SOLDIERS_PER_ROW = " + rangedUnitRows );
		rangedUnitRows += ( numRangedUnits%numSoldiersPerRow != 0 ? 1 : 0 );
		////Log.d( TAG , "numRangedUnits%NUM_SOLDIERS_PER_ROW = " + numRangedUnits%NUM_SOLDIERS_PER_ROW );
		////Log.d( TAG , "numRangedUnits%NUM_SOLDIERS_PER_ROW != 0 ? 1 : 0  = " + ( numRangedUnits%NUM_SOLDIERS_PER_ROW != 0 ? 1 : 0 ) );


		vector unitsDest ;

		//Grid grid = Rpg.getGame().getLevel().getGrid();
		boolean[][] gridTiles = grid.getGridTiles();
		float gridSize = grid.getGridSize();


		float XsignBit = dirToDest == Direction.E ? 1 : -1;

		// places melee in front of ranged based on their original loc and the dest loc direction
		switch(dirToDest)
		{
		default:
		case E:
		case W :
		{
			int i = 0;
			while( !meleeSoldiers.isEmpty() )
			{
				if( timeOutAt < System.currentTimeMillis() )
				{
					if( Game.testingVersion ){
						//Log.e( TAG , "TIMEOUT EXCEPTION!");
					}
					return locations;
				}
				for(int j = 0  ;  j  <  numSoldiersPerRow  ;  ++j )
				{
					if( timeOutAt < System.currentTimeMillis() )
					{
						if( Game.testingVersion ){
							//Log.e( TAG , "TIMEOUT EXCEPTION!");
						}
						return locations;
					}
					if( meleeSoldiers.isEmpty() )
						break ;

					unitsDest = new vector(dest) ;
					unitsDest.translate( XsignBit * i * unitSize , -unitSize * 3 + j * unitSize ) ;

					if( !isWalkable( unitsDest , gridTiles , gridSize ) )
						continue;


					locations.add( unitsDest );
					meleeSoldiers.remove(0);
				}
				i++;
			}

			XsignBit *= -1;
			i = 0;

			while( !rangedSoldiers.isEmpty() )
			{
				if( timeOutAt < System.currentTimeMillis() ){
					if( Game.testingVersion ){
						//Log.e( TAG , "TIMEOUT EXCEPTION!");
					}
					return locations;
				}


				for( int j = 0  ;  j  <  numSoldiersPerRow  ;  ++j )
				{
					if( timeOutAt < System.currentTimeMillis() ){
						if( Game.testingVersion ){
							//Log.e( TAG , "TIMEOUT EXCEPTION!");
						}
						return locations;
					}

					if( rangedSoldiers.isEmpty() )
						break ;

					unitsDest = new vector(dest) ;
					unitsDest.translate( XsignBit * unitSize + i*unitSize , -unitSize*3 + j*unitSize ) ;

					if( !isWalkable( unitsDest , gridTiles , gridSize ) )
						continue;


					locations.add( unitsDest );
					rangedSoldiers.remove(0);

				}
				i++;
			}
			break ;
		}

		case N :
		{
			int i = 0;

			while( !meleeSoldiers.isEmpty() )
			{

				if( timeOutAt < System.currentTimeMillis() ){
					if( Game.testingVersion ){
						//Log.e( TAG , "TIMEOUT EXCEPTION!");
					}
					return locations;
				}


				for(int j = 0 ; j < numSoldiersPerRow ;  ++j )
				{
					if( timeOutAt < System.currentTimeMillis() ){
						if( Game.testingVersion ){
							//Log.e( TAG , "TIMEOUT EXCEPTION!");
						}
						return locations;
					}

					if( meleeSoldiers.isEmpty() )
						break ;


					unitsDest = new vector(dest) ;
					unitsDest.translate(-unitSize*3 + j*unitSize, -meleeUnitRows*unitSize + i*unitSize) ;

					if( !isWalkable( unitsDest , gridTiles , gridSize ) )
						continue;


					meleeSoldiers.remove(0);
					locations.add( unitsDest );
				}
				++i;

			}

			i = 0;


			while( !rangedSoldiers.isEmpty() )
			{

				if( timeOutAt < System.currentTimeMillis() ){
					if( Game.testingVersion ){
						//Log.e( TAG , "TIMEOUT EXCEPTION!");
					}
					return locations;
				}


				for( int j = 0 ; j < numSoldiersPerRow ;  ++j )
				{
					if( timeOutAt < System.currentTimeMillis() ){
						if( Game.testingVersion ){
							//Log.e( TAG , "TIMEOUT EXCEPTION!");
						}
						return locations;
					}

					if( rangedSoldiers.isEmpty() )
						break ;


					unitsDest = new vector(dest) ;
					unitsDest.translate( -unitSize*3 + j*unitSize , i*unitSize );

					if( !isWalkable( unitsDest , gridTiles , gridSize ) )
						continue;

					rangedSoldiers.remove(0);
					locations.add( unitsDest );
				}

				++i;
			}

			break ;
		}


		case S :
		{
			int i = 0;

			while( !meleeSoldiers.isEmpty() )
			{

				if( timeOutAt < System.currentTimeMillis() ){
					if( Game.testingVersion ){
						//Log.e( TAG , "TIMEOUT EXCEPTION!");
					}
					return locations;
				}


				for(int j = 0 ; j < numSoldiersPerRow ;  ++j )
				{
					if( timeOutAt < System.currentTimeMillis() ){
						if( Game.testingVersion ){
							//Log.e( TAG , "TIMEOUT EXCEPTION!");
						}
						return locations;
					}

					if( meleeSoldiers.isEmpty() )
						break ;


					unitsDest = new vector(dest) ;
					unitsDest.translate( -unitSize*3 + j*unitSize , i*unitSize ) ;

					if( !isWalkable( unitsDest , gridTiles , gridSize ) )
						continue;


					meleeSoldiers.remove(0);
					locations.add( unitsDest );
				}
				++i;

			}

			i = 0;

			while( !rangedSoldiers.isEmpty() )
			{

				if( timeOutAt < System.currentTimeMillis() ){
					if( Game.testingVersion ){
						//Log.e( TAG , "TIMEOUT EXCEPTION!");
					}
					return locations;
				}


				for(int j = 0 ; j < numSoldiersPerRow ;  ++j )
				{
					if( timeOutAt < System.currentTimeMillis() ){
						if( Game.testingVersion ){
							//Log.e( TAG , "TIMEOUT EXCEPTION!");
						}
						return locations;
					}

					if( rangedSoldiers.isEmpty() )
						break ;


					unitsDest = new vector(dest) ;
					unitsDest.translate( -unitSize*3 + j*unitSize , -rangedUnitRows*unitSize + i*unitSize ) ;

					if( !isWalkable( unitsDest , gridTiles , gridSize ) )
						continue;


					rangedSoldiers.remove(0);
					locations.add( unitsDest );
				}

				++i;

			}
			break ;
		}

		}//end switch

		return locations;
	}




	private static final List<LivingThing> dummySoldiers = new ArrayList<>();

	static
	{
		Warrior w = new Warrior();

		for( int i = 0 ; i < 100 ; ++i )
			dummySoldiers.add( w );
	}

	public static List<vector> getGenericFormationPositions( vector dest , Grid grid )
	{
		try {
			return getFormationPositions( dummySoldiers , dest, grid );
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return null;
	}






	private static boolean isWalkable( vector unitsDest , boolean[][] gridTiles , float gridSize )
	{
		int i = (int) (unitsDest.x/gridSize);
		int j = (int) (unitsDest.y/gridSize);

		if( i < 0 || j < 0 || i >= gridTiles.length || j >= gridTiles[0].length )
			return false;

		else
			return gridTiles[i][j];
	}



	@Override
	public boolean checkInFormation(LivingThing lt) {
		// TODO Auto-generated method stub
		return false ;
	}
	@Override
	public void organizeTroops(LivingThing leader) {
		// TODO Auto-generated method stub

	}
	@Override
	public void addToFormation(LivingThing lt) {
		// TODO Auto-generated method stub

	}
	@Override
	public void reorganizeTroops() {
		// TODO Auto-generated method stub

	}
	@Override
	public void clearPositions() {
		// TODO Auto-generated method stub

	}





}
