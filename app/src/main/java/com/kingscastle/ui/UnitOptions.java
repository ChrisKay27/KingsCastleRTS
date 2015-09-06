package com.kingscastle.ui;

import com.kingscastle.Game;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.gameElements.livingThings.abilities.Ability.Abilities;
import com.kingscastle.gameElements.livingThings.orders.Order;
import com.kingscastle.gameElements.livingThings.orders.Order.OrderTypes;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.level.Level;
import com.kingscastle.teams.Team;
import com.kingscastle.ui.buttons.AbilityButton;
import com.kingscastle.ui.buttons.OrderButton;
import com.kingscastle.ui.buttons.SButton;

import java.util.ArrayList;
import java.util.List;


public class UnitOptions
{
	private static final String TAG = "UnitOptions";

	//private final Scroller unitOptionsScroller;

	private final List<SButton> scrollerButtons   = new ArrayList<>();
	private final List<SButton> allAbilityButtons = new ArrayList<>();
	private final List<SButton> allOrderButtons   = new ArrayList<>();


	private final List<Order> orders;
	private final List<OrderTypes> orderTypes;

	private final List<Ability> abilities;
	private final List<Abilities> abilitiesTypes;


	private int pointerID = -1 ;
	private final vector downAt = new vector( -1 , -1 );

	private final UI ui;
	private final SelectedUI selUI;


	public UnitOptions( SelectedUI selUI_ , UI ui_ )
	{
		ui = ui_;
		selUI = selUI_;
		abilities = new ArrayList<>();
		abilitiesTypes = new ArrayList<>();

		orderTypes = new ArrayList<>();
		orders = new ArrayList<>();
	}



	protected Unit lt;
	protected List<? extends Unit> lts;





	protected List<SButton> determineIfAndWhatToDisplay( Unit lt )
	{
		Game game = Rpg.getGame();
		Level level = game.getLevel();

		this.lt = lt;
		lts = null;

		if( lt == null )
			return null;

		List<SButton> buttons = new ArrayList<>();

		buttons.addAll( getAllOrderButtons( lt ) );
		buttons.addAll(getAllAbilityButtons(lt) );

		Team team = level.getMM().getTeam( lt.getTeamName() );



		//		if( Settings.yourBaseMode && lt instanceof Unit && !(lt instanceof Worker)){
		//
		//			buttons.add( AddToArmyButton.getInstance( game , (Unit) lt ,  team.getArmy() , team.getAm() ));
		//		}


		return buttons;
	}

	protected List<SButton> determineIfAndWhatToDisplay( List<? extends Unit> lts )
	{
		Game game = Rpg.getGame();
		Level level = game.getLevel();

		lt = null;
		this.lts = lts;

		if( lts == null || lts.isEmpty() )
			return null;


		ArrayList<SButton> buttons = new ArrayList<>();

		buttons.addAll( getAllOrderButtons  ( lts ) );
		buttons.addAll( getAllAbilityButtons( lts ) );

		Team team = level.getMM().getTeam( lts.get(0).getTeamName() );


		return buttons;
	}



	protected List<Unit> getUnits( List<? extends LivingThing> lts2) {

		if( lts2 != null ){
			ArrayList<Unit> units = new ArrayList<>();

			for( LivingThing lt : lts2 )
				if(lt instanceof Unit )
					units.add( (Unit) lt );

			return units;
		}
		return null;
	}




	protected List<SButton> getAllOrderButtons( Unit selectedUnit )
	{
		Game kc = Rpg.getGame();

		allOrderButtons.clear();
		orders.clear();
		orderTypes.clear();

		if( selectedUnit != null )
			if( selectedUnit.getPossibleOrders() != null )
				orders.addAll( selectedUnit.getPossibleOrders() );


		ArrayList<Unit> unitsOrdering = null ;


		for ( Order o : orders ) {
			if( orderTypes.contains( o.getOrderType() ) )
				continue;

			OrderButton abButton = OrderButton.getInstance( kc.getActivity() , o , selectedUnit , unitsOrdering , ui.getUnitOrders());

			allOrderButtons.add( abButton );

			orderTypes.add( o.getOrderType() );
		}

		return allOrderButtons;
	}


	protected List<SButton> getAllOrderButtons( List<? extends Unit> selectedUnits )
	{
		Game kc = Rpg.getGame();

		allOrderButtons.clear();
		orders.clear();
		orderTypes.clear();

		if( selectedUnits == null )	return allOrderButtons;


		for( LivingThing lt : selectedUnits )
			if( lt.getPossibleOrders() != null )
				orders.addAll( lt.getPossibleOrders() );


		List<? extends Unit> unitsOrdering = selectedUnits;

		OrderButton abButton;

		for ( Order o : orders )
		{
			if( orderTypes.contains( o.getOrderType() ) )
				continue;

			abButton = OrderButton.getInstance( kc.getActivity() , o , null , unitsOrdering , ui.getUnitOrders() );

			allOrderButtons.add( abButton );

			orderTypes.add( o.getOrderType() );
		}


		return allOrderButtons;
	}





	protected List<SButton> getAllAbilityButtons( Unit selectedUnit  )
	{
        Game kc = Rpg.getGame();
		allAbilityButtons.clear();

		abilities.clear();
		abilitiesTypes.clear();

		if( selectedUnit != null )
			abilities.addAll( selectedUnit.getAbilities() );


		for ( Ability ab : abilities )
		{
			if( abilitiesTypes.contains( ab.getAbility() ) )
				continue;

			AbilityButton abButton = AbilityButton.getInstance( kc.getActivity() , ab );
			allAbilityButtons.add(abButton);
			abilitiesTypes.add( ab.getAbility() );
		}

		return allAbilityButtons;
	}

	protected List<SButton> getAllAbilityButtons( List<? extends Unit> selectedUnits )
	{
        Game kc = Rpg.getGame();
		allAbilityButtons.clear();

		abilities.clear();
		abilitiesTypes.clear();


		if( selectedUnits != null )
			for(LivingThing lt : selectedUnits )
				abilities.addAll( lt.getAbilities() );



		for ( Ability ab : abilities )
		{
			if( abilitiesTypes.contains( ab.getAbility() ) )
				continue;

			AbilityButton abButton = AbilityButton.getInstance( kc.getActivity() , ab );
			allAbilityButtons.add(abButton);
			abilitiesTypes.add( ab.getAbility() );
		}

		return allAbilityButtons;
	}





	public void showScroller( Unit lt ){
		List<? extends SButton> buttons = determineIfAndWhatToDisplay( lt );
		if( buttons != null )
			selUI.displayTheseInRightScroller( buttons , TAG );
	}

	public void showScroller( List<? extends Unit> lts ){
		List<? extends SButton> buttons = determineIfAndWhatToDisplay( lts );
		if( buttons != null )
			selUI.displayTheseInRightScroller( buttons , TAG );
	}




	public void hideScroller(){
		selUI.hideMyScrollerButtons( TAG );
	}

	public void resetScroller(){
		if( TAG.equals(selUI.getButtonsId()) )
			if( lt != null )
				showScroller( lt );
			else
				showScroller( lts );
	}






	public void pointerLeftScreen(int pointer)
	{
		if( pointer == pointerID ){
			pointerID = -1;
			downAt.set( -1 , -1 );
		}
	}























}
