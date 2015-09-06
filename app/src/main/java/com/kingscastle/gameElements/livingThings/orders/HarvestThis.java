package com.kingscastle.gameElements.livingThings.orders;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.RectF;
import android.util.Log;

import com.kaebe.framework.Image;
import com.kaebe.framework.Input.TouchEvent;
import com.kaebe.kingscastle27.gameElements.GameElement;
import com.kaebe.kingscastle27.gameElements.resources.Workable;
import com.kaebe.kingscastle27.gameElements.resources.Workable.RT;
import com.kaebe.kingscastle27.livingThings.LivingThing;
import com.kaebe.kingscastle27.livingThings.army.Worker;
import com.kaebe.kingscastle27.livingThings.buildings.Building;
import com.kaebe.kingscastle27.livingThings.buildings.Farm;
import com.kaebe.kingscastle27.managment.GemManager.GemPackage;
import com.kaebe.kingscastle27.managment.ListPkg;
import com.kaebe.kingscastle27.managment.MM;
import com.kaebe.kingscastle27.map.level.CD;
import com.kaebe.kingscastle27.physics.Vector;
import com.kaebe.kingscastle27.teams.Teams;
import com.kaebe.kingscastle27.util.CoordConverter;
import com.kaebe.kingscastlelib.Rpg;

public final class HarvestThis extends Order
{

	private static final String TAG = "HarvestThis";

	private static Image iconImage;

	//private static HarvestThis harvestThis;

	private final ArrayList<Worker> workers = new ArrayList<Worker>();

	//private Teams teamName;


	private HarvestThis()
	{
	}

	public HarvestThis(Teams team2)
	{
		//	teamName = team2;
	}


	public static HarvestThis getInstance()
	{
		//		if( harvestThis == null )
		//		{
		//			harvestThis = new HarvestThis( Teams.Good );
		//
		//		}
		return new HarvestThis( );
	}



	@Override
	public boolean analyseTouchEvent( TouchEvent event , CoordConverter cc, CD cd)
	{
		cc.getCoordsScreenToMap( event.x , event.y , mapRel );

		return analyseCoordinate( mapRel , workers );
	}





	public static boolean analyseCoordinate( Vector mapRelCoords , ArrayList<? extends LivingThing> thingsToCommand )
	{
		Log.v( TAG , "analyseCoordinate(mapRelCoords="+mapRelCoords + "thingsToCommand="+thingsToCommand+")");
		if( mapRelCoords == null )
			throw new IllegalArgumentException( "mapRelCoords == null" );

		if( thingsToCommand == null )
			throw new IllegalArgumentException( "thingsToCommand == null" );

		if( thingsToCommand.size() == 0 )
			throw new IllegalArgumentException( " thingsToCommand.size() == 0 " );




		RectF area;
		Workable temp;
		Teams calledFromTeam = thingsToCommand.get(0).getTeamName();


		GemPackage gemPkg = Rpg.getMM().getGem().getGameElements();

		synchronized( gemPkg )
		{
			GameElement[] gems = gemPkg.gems;
			int gesSize = gemPkg.size;

			float x = mapRelCoords.x;
			float y = mapRelCoords.y;

			for( int i = 0 ; i < gesSize ; ++i )
			{
				GameElement ge = gems[i];

				if( ge instanceof Workable )
				{
					temp = (Workable) ge;

					if( temp.isDead() )
						continue;

					if( temp.getResourceType() == RT.FOOD )
					{
						if ( temp.isABuilding() )
							if( ((Building) temp).getTeamName() != calledFromTeam )
								continue;


						area = ge.area;

						if( area.contains( x, y ) )
						{
							if( ge instanceof Farm )
								return makeOneOfTheseFarmThis( Order.getWorkersFrom( thingsToCommand , null ) , (Farm) ge );
							else
								return makeEveryoneHarvestThis( Order.getWorkersFrom( thingsToCommand , null ) , (Workable) ge );
						}
					}
				}
			}
		}

		ListPkg<Building> bPkg = MM.get().getBuildingsOnTeam( calledFromTeam );

		synchronized( bPkg )
		{
			Building[] buildings = bPkg.list;
			int size = bPkg.size;

			for( int i = 0 ; i < size ; ++i )
			{
				Building b = buildings[i];

				if( b.isDead() )
					continue;

				if( b instanceof Workable )
				{
					temp = b;

					if( temp.getResourceType() == RT.FOOD )
					{
						area = b.area;

						if ( area.contains( mapRelCoords.x, mapRelCoords.y ))
						{
							if( temp instanceof Farm )
								return makeOneOfTheseFarmThis( Order.getWorkersFrom( thingsToCommand , null ) , (Farm) b );
							else
								return makeEveryoneHarvestThis( Order.getWorkersFrom( thingsToCommand , null ) , b );
						}
					}
				}
			}
		}


		return false;
	}


	private static boolean makeOneOfTheseFarmThis( ArrayList<Worker> workers , Farm farm )
	{
		if( farm == null || workers == null )
			return false;


		if( workers.size() == 0 )
			return false;

		for( Worker w : workers ){
			w.harvestThis( farm );
		}


		return true;

	}


	private static boolean makeEveryoneHarvestThis(ArrayList<Worker> workers, Workable foodSource)
	{
		Log.v( TAG , "makeEveryoneHarvestThis("+workers.size()+"," + foodSource + ")");
		if( workers.size() == 0 )
			return false;

		if( foodSource instanceof Farm )
		{
			Farm farm = (Farm) foodSource;

			for( Worker w : workers ){
				w.harvestThis( farm );
			}
			return true;
		}

		return false;
	}



	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage( R.drawable.harvest_this_icon );
		}
		return iconImage;
	}



	@Override
	public ArrayList<? extends LivingThing> getUnitsToBeOrdered()
	{
		return workers;
	}




	@Override
	public void setUnitsToBeOrdered( ArrayList<? extends LivingThing> newWorkers )
	{

		workers.clear();

		for( LivingThing worker : newWorkers)
		{
			if( worker instanceof Worker)
			{
				workers.add( (Worker) worker );
			}

		}
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





	public void makeEveryoneHarvestThis( Workable foodSource )
	{
		makeEveryoneHarvestThis( workers , foodSource );
	}





	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException
	{


	}


	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.HARVEST_THIS;
	}

	private static final String name = "Collect Food";

	@Override
	public String toString()
	{
		return name;
	}


}
