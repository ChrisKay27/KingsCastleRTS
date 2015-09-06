package com.kingscastle.gameElements.livingThings.orders;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.kaebe.framework.Image;
import com.kaebe.framework.Input.TouchEvent;
import com.kaebe.kingscastle27.gameElements.GameElement;
import com.kaebe.kingscastle27.gameElements.resources.Mine;
import com.kaebe.kingscastle27.livingThings.LivingThing;
import com.kaebe.kingscastle27.livingThings.army.Worker;
import com.kaebe.kingscastle27.managment.GemManager.GemPackage;
import com.kaebe.kingscastle27.map.level.CD;
import com.kaebe.kingscastle27.physics.Vector;
import com.kaebe.kingscastle27.util.CoordConverter;
import com.kaebe.kingscastlelib.Rpg;

public class MineThis extends Order
{

	private static Image iconImage;

	private final ArrayList<Worker> workers = new ArrayList<Worker>();

	private static MineThis mineThis;

	private MineThis()
	{

	}


	public static MineThis getInstance()
	{
		if( mineThis == null )
		{
			mineThis = new MineThis();
		}
		return mineThis;
	}




	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.mine_this_icon);
		}
		return iconImage;
	}



	@Override
	public ArrayList<? extends LivingThing> getUnitsToBeOrdered()
	{
		return workers;
	}




	@Override
	public void setUnitsToBeOrdered(ArrayList<? extends LivingThing> newWorkers)
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

	@Override
	public boolean analyseTouchEvent(TouchEvent event, CoordConverter cc, CD cd)
	{
		cc.getCoordsScreenToMap( event.x , event.y , mapRel );

		return analyseCoordinate( mapRel , workers );
	}



	public static boolean analyseCoordinate( Vector mapRelCoords , ArrayList<? extends LivingThing> thingsToCommand )
	{
		if( mapRelCoords == null )
		{
			throw new IllegalArgumentException( "mapRelCoords == null" );
		}
		if( thingsToCommand == null )
		{
			throw new IllegalArgumentException( "thingsToCommand == null" );
		}
		if( thingsToCommand.size() == 0 )
		{
			throw new IllegalArgumentException( " thingsToCommand.size() == 0 " );
		}


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

				if( ge instanceof Mine )
				{
					if( ge.area.contains( x , y ))
					{
						makeEveryoneMineThis( Order.getWorkersFrom( thingsToCommand , null ) , (Mine) ge );
						return true;
					}
				}
			}
		}

		return false;
	}




	private static void makeEveryoneMineThis( ArrayList<Worker> workers , Mine mine )
	{

		for( Worker worker : workers )
		{
			worker.clearJobAndPaths();
			worker.mineThis(mine);
		}

	}




	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException {
		// TODO Auto-generated method stub

	}


	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.MINE_THIS;
	}


	private static final String name = "Mine";

	@Override
	public String toString()
	{
		return name;
	}


}
