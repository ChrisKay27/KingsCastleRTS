package com.kingscastle.gameElements.livingThings.orders;

import com.kingscastle.framework.Image;
import com.kingscastle.framework.Input.TouchEvent;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameUtils.CoordConverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class ChangeStance extends Order {

	private static final Image freeIconImage = null;//Assets.loadImage(R.drawable.stance_free);
	private static final Image guardLocationImage =null;// Assets.loadImage( R.drawable.guard_location );
	private static final Image holdPositionIconImage =null;//= Assets.loadImage( R.drawable.guard_location );

	private final ArrayList<Unit> soldiers = new ArrayList<Unit>();

	private Stance currStance = Stance.GUARD_LOCATION;



	public static ChangeStance getInstance() {
		//		if( cancelAction == null )
		//		{
		//			cancelAction =
		//		}
		return new ChangeStance();
	}




	@Override
	public Image getIconImage()
	{
		switch( currStance )
		{
		case GUARD_LOCATION:
			return getGuardLocationImage();
		case PLAYING_THE_OBJECTIVE:
		default:
		case FREE:
			return getFreeIconImage();

		case HOLD_POSITION:
			return getHoldPositionIconImage();
		}
	}


	@Override
	public List<? extends Unit> getUnitsToBeOrdered()
	{
		return soldiers;
	}




	@Override
	public void setUnitsToBeOrdered(List<? extends Unit> soldiers )
	{

		this.soldiers.clear();

		int freeCount = 0;
		int holdPositionCount = 0;
		int guardPositionCount = 0;

		for( Unit lt : soldiers )	{

            Unit u = (Unit) lt;
            this.soldiers.add( u );

            switch( u.getStance() )
            {
            default:
            case FREE:
                ++freeCount;
                break;

            case HOLD_POSITION:
                ++holdPositionCount;
                break;
            case PLAYING_THE_OBJECTIVE:
                //++playingTheObjCount;
                break;
            case GUARD_LOCATION:
                ++guardPositionCount;
                break;
            }

		}


		if( freeCount > holdPositionCount && freeCount > guardPositionCount )
		{
			currStance = Stance.FREE ;
		}
		else if( holdPositionCount > freeCount && holdPositionCount > guardPositionCount )
		{
			currStance =  Stance.HOLD_POSITION ;
		}
		else if( guardPositionCount > freeCount && guardPositionCount > holdPositionCount )
		{
			currStance =  Stance.GUARD_LOCATION ;
		}
		else
		{
			currStance = Stance.FREE ;
		}
	}



	@Override
	public void setUnitToBeOrdered( Unit unit )
	{
	if ( !(unit instanceof Unit) )
		{
			return ;
		}
		else
		{
			soldiers.clear();
			Unit u = (Unit) unit;
			soldiers.add( u );
			currStance = u.getStance();
		}

	}



	@Override
	public boolean analyseTouchEvent( TouchEvent event , CoordConverter cc, CD cd)
	{
		switchEveryonesStance( soldiers );

		//		UnitOptions uo = UnitOptions.get();
		//		uo.showScroller( soldiers );

		return true;
	}


	public void makeEveryoneCancel( )
	{
		switchEveryonesStance( soldiers );
	}



	void switchEveryonesStance(List<Unit> soldiers)
	{
		currStance = currStance.getNext();
		for( Unit unit : soldiers )
			unit.setStance( currStance );
	}




	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException
	{
	}


	@Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.CHANGE_STANCE;
	}




	private static final String name = "Stance";
	private static final String free = " Free";
	private static final String hold = " Hold Position";
	private static final String guard = " Guard Position";
	private static final String playObjective = " Objectives";
	private static final String nil = "";

	@Override
	public String toString()
	{
		return nil;
		//		switch( currStance )
		//		{
		//		case FREE:
		//			return name + free;
		//
		//		case HOLD_POSITION:
		//			return name + hold;
		//
		//		case PLAYING_THE_OBJECTIVE:
		//			return name + playObjective;
		//
		//		case GUARD_LOCATION:
		//			return name + guard;
		//
		//		default:
		//			return name;
		//		}


	}




	public static Image getFreeIconImage() {
		return freeIconImage;
	}






	public static Image getHoldPositionIconImage() {

		return holdPositionIconImage;
	}




	private static Image getGuardLocationImage() {
		return guardLocationImage;
	}







}
