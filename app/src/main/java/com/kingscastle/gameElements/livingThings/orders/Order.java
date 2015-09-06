package com.kingscastle.gameElements.livingThings.orders;


import com.kingscastle.framework.Image;
import com.kingscastle.framework.Input.TouchEvent;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.movement.pathing.Grid;
import com.kingscastle.gameUtils.CoordConverter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public abstract class Order
{
	public enum OrderTypes	{
		MINE_THIS , LOG_THIS , BUILD_THIS , GO_HERE , ATTACK_THIS, HARVEST_THIS, CANCEL, REPAIR_THIS, GARRISON_HERE, HOLD_POSITION_STANCE, DEPOSIT_RESOURCES, CHANGE_STANCE, STAY_NEAR_HERE, BUILD_DECO
	}

	public abstract Image getIconImage();

	public abstract void saveYourSelf(BufferedWriter b) throws IOException;

	public abstract List<? extends Unit> getUnitsToBeOrdered();

	public abstract void setUnitToBeOrdered( Unit unit );

	public boolean analyseTouchEvent( TouchEvent event , CoordConverter cc ,CD cd){
		return false;
	}
	public boolean analyseTouchEvent(TouchEvent event, CoordConverter cc ,CD cd , Grid grid){
		return false;
	}

	public abstract void setUnitsToBeOrdered(List<? extends Unit> livingThings);

	public abstract OrderTypes getOrderType();














}
