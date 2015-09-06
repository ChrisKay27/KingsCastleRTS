package com.kingscastle.gameElements.livingThings.orders;

import com.kingscastle.framework.Image;
import com.kingscastle.framework.Input;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameUtils.CoordConverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class CancelAction extends Order
{

	private static Image iconImage;
    private final List<Unit> units = new ArrayList<>();

    //private static CancelAction cancelAction;




	public static CancelAction getInstance()
	{
		//		if( cancelAction == null )
		//		{
		//			cancelAction =
		//		}
		return new CancelAction();
	}




	@Override
	public Image getIconImage()
	{
//		if( iconImage == null )
//			iconImage = Assets.loadImage(R.drawable.stop_icon);

		return iconImage;
	}



	@Override
	public List<? extends Unit> getUnitsToBeOrdered()
	{
		return units;
	}




	@Override
	public void setUnitsToBeOrdered(List<? extends Unit> units)
	{
        this.units.clear();
		this.units.addAll(units);
	}



	@Override
	public void setUnitToBeOrdered(Unit u)
	{
		units.clear();
		units.add( u);
	}



	@Override
	public boolean analyseTouchEvent( Input.TouchEvent event , CoordConverter cc, CD cd)
	{
		makeEveryoneCancel(units);
		//UnitOptions.get().resetScroller();
		return true;
	}


	public void makeEveryoneCancel( )
	{
		makeEveryoneCancel(units);
	}



	void makeEveryoneCancel(List<Unit> units)
	{

		for( Unit u : units )
		{

		}

	}




	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException
	{
	}



	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.CANCEL;
	}



	private static final String name = "";//"Cancel Action";

	@Override
	public String toString()
	{
		return name;
	}


}
