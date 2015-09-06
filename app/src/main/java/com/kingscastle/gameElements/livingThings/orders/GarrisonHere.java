package com.kingscastle.gameElements.livingThings.orders;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.kaebe.framework.Image;
import com.kaebe.framework.Input.TouchEvent;
import com.kaebe.kingscastle27.gameElements.GameElement;
import com.kaebe.kingscastle27.livingThings.LivingThing;
import com.kaebe.kingscastle27.livingThings.Unit;
import com.kaebe.kingscastle27.livingThings.buildings.Garrison;
import com.kaebe.kingscastle27.map.level.CD;
import com.kaebe.kingscastle27.movement.pathing.Grid;
import com.kaebe.kingscastle27.physics.Vector;
import com.kaebe.kingscastle27.util.CoordConverter;

public final class GarrisonHere extends Order
{



	//private static BuildThis buildThis;


	private final ArrayList<LivingThing> unitsToBeOrdered = new ArrayList<LivingThing>();




	public static GarrisonHere getInstance()
	{
		return new GarrisonHere();
	}




	@Override
	public Image getIconImage()
	{
		return null;
	}



	@Override
	public ArrayList<? extends LivingThing> getUnitsToBeOrdered()
	{
		return unitsToBeOrdered;
	}




	@Override
	public void setUnitsToBeOrdered(ArrayList<? extends LivingThing> livingThings)
	{

		unitsToBeOrdered.clear();

		if( livingThings == null )
		{
			return;
		}

		unitsToBeOrdered.addAll( livingThings );
	}



	@Override
	public void setUnitToBeOrdered(LivingThing person)
	{

		unitsToBeOrdered.clear();
		unitsToBeOrdered.add( person );

	}


	private final Vector mapRel = new Vector();


	@Override
	public boolean analyseTouchEvent(TouchEvent event, CoordConverter cc, CD cd) {
		return false;
	}

	@Override
	public boolean analyseTouchEvent( TouchEvent event , CoordConverter cc , CD cd , Grid grid )
	{
		if( unitsToBeOrdered.isEmpty() )
			return false;

		cc.getCoordsScreenToMap( event.x , event.y , mapRel );

		return analyseCoordinate ( mapRel , unitsToBeOrdered , cd ,  grid );

	}



	public static boolean analyseCoordinate( Vector mapRelCoord , ArrayList<? extends LivingThing> thingsToCommand , CD cd , Grid grid )
	{

		GameElement ge = cd.checkPlaceableOrTarget( mapRelCoord );

		boolean commandedSomething = false;

		if( ge != null && ge instanceof Garrison )
		{
			if( !ge.dead )
				if( ge.getTeamName() == thingsToCommand.get( 0 ).getTeamName() )
					for( LivingThing lt : thingsToCommand )
						if( lt instanceof Unit )
						{
							( (Unit) lt ).goGarrisonInsideOf( ( (Garrison) ge ));
							commandedSomething = true;
						}
		}

		return commandedSomething;
	}




	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException{
	}


	@Override
	public OrderTypes getOrderType(){
		return OrderTypes.GARRISON_HERE;
	}

	private static final String name = "Garrison Inside Of";

	@Override
	public String toString(){
		return name;
	}









}
