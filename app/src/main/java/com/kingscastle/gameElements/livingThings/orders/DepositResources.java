package com.kingscastle.gameElements.livingThings.orders;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.kaebe.framework.Image;
import com.kaebe.framework.Input.TouchEvent;
import com.kaebe.kingscastle27.R;
import com.kaebe.kingscastle27.livingThings.LivingThing;
import com.kaebe.kingscastle27.livingThings.army.Worker;
import com.kaebe.kingscastle27.map.level.CD;
import com.kaebe.kingscastle27.util.CoordConverter;
import com.kaebe.kingscastlelib.Assets;

public class DepositResources extends Order
{

	private static final Image iconImage = Assets.loadImage( R.drawable.deposit_resources );

	//private static CancelAction cancelAction;



	private final ArrayList<Worker> workers = new ArrayList<Worker>();




	public static DepositResources getInstance()
	{
		//		if( cancelAction == null )
		//		{
		//			cancelAction =
		//		}
		return new DepositResources();
	}




	@Override
	public Image getIconImage()
	{
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



	@Override
	public boolean analyseTouchEvent( TouchEvent event , CoordConverter cc, CD cd)
	{
		makeEveryoneDepositResources( workers );
		//UnitOptions.get().resetScroller();
		return true;
	}


	public void makeEveryoneDepositResources( )
	{
		makeEveryoneDepositResources( workers  );
	}



	void makeEveryoneDepositResources(ArrayList<Worker> workers)
	{

		for( Worker worker : workers )
		{
			worker.setMustDropOffResources ( true );
		}

	}




	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException
	{

	}


	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.DEPOSIT_RESOURCES;
	}


	private static final String name = "";//"Deposit Resources";

	@Override
	public String toString()
	{
		return name;
	}

}
