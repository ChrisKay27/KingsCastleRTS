package com.kingscastle.ui;

import android.util.Log;

import com.kingscastle.framework.Input.TouchEvent;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Healer;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.livingThings.orders.AttackThis;
import com.kingscastle.gameElements.livingThings.orders.GoHere;
import com.kingscastle.gameElements.movement.pathing.Grid;
import com.kingscastle.gameUtils.CoordConverter;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.teams.Teams;

import java.util.ArrayList;
import java.util.List;


public class UnitCommands implements TouchEventAnalyzer
{
	private static final String TAG = "UnitCommands";


	private vector mapRelCoord  = new vector();

	private final vector downAt = new vector();
	private int pointerId;

	private final UI ui;
	private Grid grid;
	private final CoordConverter cc;
	private final Selecter selecter;
	private final UnitOptions uo;



	UnitCommands( UI ui_ , CoordConverter cc_ , Selecter selecter_ , UnitOptions uo_ )	{
		ui = ui_;

		this.cc = cc_;
		uo = uo_;
		selecter = selecter_;
	}



	public boolean analyzeTouchEvent( TouchEvent e )
	{
		if( !ui.somethingIsSelected() ){
			Log.d( TAG , "Unit Commands told to act and nothing is selected.");
			return false;
		}


		if( e.type == TouchEvent.TOUCH_DOWN )
		{
			//Log.d( TAG , "Down at " + e.x + "," +  e.y );
			downAt.set( e.x , e.y );
			pointerId = e.pointer;
		}
		else if( e.type == TouchEvent.TOUCH_UP && pointerId == e.pointer )
		{
			if( downAt.distanceSquared( e.x , e.y ) > Rpg.fifteenDpSquared )
			{
				//Log.d( TAG , "Not down and up in same location");
				return false;
			}


			mapRelCoord = new vector();
			mapRelCoord = cc.getCoordsScreenToMap( e.x , e.y , mapRelCoord );

			//temp = CollisionDetector.getInstance().checkPlaceableOrTarget( vtemp ); // might need to be removed
			//Log.d( TAG , "Informing selected of location " + mapRelCoord );
			return informSelectedOfALocation( mapRelCoord );

			//			{
			//				setTargetOfSelectedTo( (LivingThing) temp);
			//				return true;
			//			}
			//
			//			if( temp == null )
			//			{
			//				moveSelectedUnits( vtemp );
			//				return true;
			//			}

		}

		return false;
	}





	private final List<Unit> selectedUnits = new ArrayList<>();

	private boolean informSelectedOfALocation( vector mapRelCoord )
	{
		selectedUnits.clear();

		Unit selected = ui.selectedThings.getSelectedUnit();

		if( selected != null )
		{
			//Log.d( TAG , "selected = " + selected );
			if( mapRelCoord.distanceSquared( selected.loc ) < Rpg.fifteenDpSquared ){
				////Log.d( TAG , "retapped on selected, clearing selection" );
				selecter.clearSelection();
				return true;
			}

            selectedUnits.add( selected );
		}
		else
		{
			//Log.d( TAG , "multiple things must be selected");
			List<Unit> units = ui.getSelectedUnits() ;

			if( units != null ) {
				//Log.d( TAG , "they are " + selectedUnits);
				selectedUnits.addAll( units );
			}
		}


		if( selectedUnits.size() > 0 )	{

			boolean success = AttackThis.analyseCoordinate(ui.getCD(), mapRelCoord, selectedUnits);
			if( success )
			{
				uo.showScroller( selectedUnits );
				Log.d( TAG , "AttackThis used up touch event.");
				return true;
			}


			GameElement ge = ui.getCD().checkPlaceableOrTarget( mapRelCoord );
			if( ge != null && ge.getTeamName() == Teams.BLUE )
			{
				if( ge == selected )
					selecter.clearSelection();
				else
				{
					selecter.setSelected( ge );
					return true;
				}
			}

            if( grid == null )
                grid = ui.getMM().getGridUtil().getGrid();

			boolean isWalkable = grid.isWalkable( mapRelCoord );
			if( isWalkable ){
				success = GoHere.analyseCoordinate(mapRelCoord, selectedUnits, ui.getCD(), grid, ui.getMapWidth(), ui.getMapHeight() );
				if( success )
				{
//					for( LivingThing lt : selectedUnits )
//						if( lt instanceof Unit )
//							((Unit) lt).setGarrisonBuilding( null );

					///Log.d( TAG , "GoHere used up touch event.");
					return true;
				}
			}


		}

		Log.d( TAG , "Nothing used up touch event.");
		return false;

	}






	/**
	 * 
	 * @param X if null false is returned.
	 * @param Y if null X's target/healingtarget will be set to null.
	 * @return True if the target was set to an actual creature and not null.
	 */
	public static boolean setTargetOfXToY( LivingThing X , LivingThing Y)
	{
		if( X == null )
		{
			return false;
		}
		if( Y == null )
		{
			X.setTarget( null );

			if( X instanceof Healer)
			{
				((Healer) X).setHealingTarget( null );
			}

			return false;
		}

		if( X instanceof Healer)
		{
			if( X.getTeamName() == Y.getTeamName() )
			{
				((Healer) X).setHealingTarget(Y);
				return true;
			}
			else
			{
				X.setTarget(Y);
				return true;
			}
		}
		else
		{
			if( X.getTeamName() != Y.getTeamName() )
			{
				X.setTarget(Y);
				return true;
			}
		}
		return false;
	}


}
