package com.kingscastle.gameElements.livingThings.orders;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.kaebe.framework.Image;
import com.kaebe.framework.Input.TouchEvent;
import com.kaebe.kingscastle27.R;
import com.kaebe.kingscastle27.livingThings.LivingThing;
import com.kaebe.kingscastle27.livingThings.army.Worker;
import com.kaebe.kingscastle27.livingThings.buildings.Building;
import com.kaebe.kingscastle27.livingThings.buildings.PendingBuilding;
import com.kaebe.kingscastle27.managment.ListPkg;
import com.kaebe.kingscastle27.managment.MM;
import com.kaebe.kingscastle27.map.level.CD;
import com.kaebe.kingscastle27.physics.Vector;
import com.kaebe.kingscastle27.util.CoordConverter;
import com.kaebe.kingscastlelib.Assets;

public final class BuildThis extends Order
{

	private static Image iconImage;

	private static String TAG = "BuildThis";

	//private static BuildThis buildThis;


	private final ArrayList<Worker> workers = new ArrayList<Worker>();



	public BuildThis(ArrayList<Worker> workers_){
		workers.addAll(workers_);
	}



	public BuildThis() {
	}



	public static BuildThis get(){
		return new BuildThis();
	}




	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
			iconImage = Assets.loadImage( R.drawable.build_buildings_button );

		return iconImage;
	}



	@Override
	public ArrayList<Worker> getUnitsToBeOrdered(){
		return workers;
	}





	@Override
	public void setUnitsToBeOrdered(ArrayList<? extends LivingThing> newWorkers)
	{
		workers.clear();

		for( LivingThing worker : newWorkers)
			if( worker instanceof Worker)
				workers.add( (Worker) worker );

	}




	@Override
	public void setUnitToBeOrdered(LivingThing worker)
	{
		if ( !(worker instanceof Worker) )
		{
			return ;
		}
		else
		{
			workers.clear();
			workers.add( (Worker) worker );
		}

	}




	private final Vector mapRel = new Vector();


	@Override
	public boolean analyseTouchEvent(TouchEvent event, CoordConverter cc, CD cd)
	{

		cc.getCoordsScreenToMap( event.x , event.y , mapRel );

		return analyseCoordinate( mapRel , workers );

	}



	public static boolean analyseCoordinate( Vector mapRelCoords , ArrayList<? extends LivingThing> thingsToCommand )
	{
		if( mapRelCoords == null )
			throw new IllegalArgumentException( "mapRelCoords == null" );

		if( thingsToCommand == null )
			throw new IllegalArgumentException( "thingsToCommand == null" );

		if( thingsToCommand.size() == 0 )
			throw new IllegalArgumentException( " thingsToCommand.size() == 0 " );

		ArrayList<Worker> wrkers = Order.getWorkersFrom( thingsToCommand , null );

		if( wrkers == null || wrkers.size() == 0 )
			return false;

		ListPkg<Building> bPkg = MM.get().getBuildingsOnTeam( thingsToCommand.get(0).getTeamName() );
		////Log.d( TAG , "analyseCoordinate mapRelCoords:" + mapRelCoords + "thingsToCommand=" + thingsToCommand + " bPkg=" + bPkg);

		synchronized( bPkg )
		{
			Building[] buildings = bPkg.list;
			int size = bPkg.size;

			for( int i = 0 ; i < size ; ++i )
			{
				Building b = buildings[i];

				if( b instanceof PendingBuilding )
				{
					if ( b.area.contains( mapRelCoords.x, mapRelCoords.y ))
					{
						makeEveryoneBuildThis( wrkers , (PendingBuilding) b );
						//Log.d( TAG , "Making all selected workers build a " + b );
						return true;
					}
				}
			}
		}

		return false;
	}



	public void makeEveryoneBuildThis( PendingBuilding pendingBuilding )
	{
		makeEveryoneBuildThis( workers , pendingBuilding );
	}



	public static void makeEveryoneBuildThis( ArrayList<Worker> workers , PendingBuilding pendingBuilding )
	{
		//Log.v( TAG , "makeEveryoneBuildThis() workers=" + workers + " pendingBuilding=" + pendingBuilding);
		for( Worker worker : workers )
		{
			worker.clearJobAndPaths();
			worker.buildThis( pendingBuilding );
		}
	}




	@Override
	public void saveYourSelf( BufferedWriter b ) throws IOException
	{
	}


	private static final String name = "";//"Build Buildings";


	@Override
	public String toString()
	{
		return name;
	}


	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.BUILD_THIS;
	}


}
