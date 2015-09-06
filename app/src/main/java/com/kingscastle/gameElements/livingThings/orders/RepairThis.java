package com.kingscastle.gameElements.livingThings.orders;

import android.util.Log;

import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.army.Worker;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameElements.managment.ListPkg;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.CoordConverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public final class RepairThis extends Order
{

	private static final String TAG = "RepairThis";

	private static Image iconImage;

	//private static BuildThis buildThis;


	private final ArrayList<Worker> workers = new ArrayList<>();




	public static RepairThis getInstance()
	{
		return new RepairThis();
	}




	@Override
	public Image getIconImage()
	{
		if( iconImage == null )		{
			//iconImage = Assets.loadImage( R.drawable.build_this_icon );
		}
		return iconImage;
	}



	@Override
	public List<? extends LivingThing> getUnitsToBeOrdered()
	{
		return workers;
	}




	@Override
	public void setUnitsToBeOrdered(List<? extends LivingThing> newWorkers)
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
			return ;
		else
		{
			workers.clear();
			workers.add( (Worker) worker );
		}

	}




	private final Vector mapRel = new Vector();


	@Override
	public boolean analyseTouchEvent( TouchEvent event , CoordConverter cc, CD cd)
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


		ListPkg<Building> bPkg = MM.get().getBuildingsOnTeam( thingsToCommand.get(0).getTeamName() );

		synchronized( bPkg )
		{
			Building[] buildings = bPkg.list;
			int size = bPkg.size;

			for( int i = 0 ; i < size ; ++i )
			{
				Building b = buildings[i];

				if( !(b instanceof PendingBuilding) )
				{
					if( b.getLQ().getHealth() == b.getLQ().getFullHealth() )
						continue;

					if ( b.area.contains( mapRelCoords.x, mapRelCoords.y ))
					{
						ArrayList<Worker> workers = Order.getWorkersFrom( thingsToCommand , null );

						if( workers != null && workers.size() > 0 )
						{
							makeEveryoneRepairThis( workers , b );
							return true;
						}
					}
				}
			}
		}
		return false;
	}



	public void makeEveryoneBuildThis( Building building )
	{
		makeEveryoneRepairThis( workers , building );
	}



	private static void makeEveryoneRepairThis(ArrayList<Worker> workers, Building building)
	{
		Log.d(TAG , "Making " + workers.size() + " repair a " + building );
		for( Worker worker : workers )
			worker.repairThis( building );
	}




	@Override
	public void saveYourSelf( BufferedWriter b ) throws IOException
	{

	}


	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.REPAIR_THIS;
	}

	private static final String name = "Repair Building";

	@Override
	public String toString()
	{
		return name;
	}
}
