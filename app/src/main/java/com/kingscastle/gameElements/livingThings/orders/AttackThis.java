package com.kingscastle.gameElements.livingThings.orders;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Image;
import com.kingscastle.framework.Input;
import com.kingscastle.gameElements.CD;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Humanoid;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.CoordConverter;
import com.kingscastle.gameUtils.vector;
import com.kingscastle.ui.EnemyAtLocationChecker;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class AttackThis extends Order
{

	//	private static final String TAG = "AttackThis";

	private static Image iconImage;

	//private static BuildThis buildThis;


	private final List<Unit> unitsToBeOrdered = new ArrayList<>();




	@NonNull
    public static AttackThis getInstance()
	{
		return new AttackThis();
	}




	@Override
	public Image getIconImage()	{
		if( iconImage == null )	{
			//	iconImage = Assets.loadImage( R.drawable.attack_icon );
		}
		return iconImage;
	}



	@NonNull
    @Override
	public List<? extends Unit> getUnitsToBeOrdered() {
		return unitsToBeOrdered;
	}




	@Override
	public void setUnitsToBeOrdered(@NonNull List<? extends Unit> livingThings) {
		unitsToBeOrdered.clear();
		unitsToBeOrdered.addAll( livingThings );
	}



	@Override
	public void setUnitToBeOrdered(Unit person) {
		unitsToBeOrdered.clear();
		unitsToBeOrdered.add( person );
	}


	private final vector mapRel = new vector();



	public boolean analyseTouchEvent(@NotNull MM mm, @NonNull Input.TouchEvent event, @NonNull CoordConverter cc , @NotNull CD cd )	{
		cc.getCoordsScreenToMap( event.x , event.y , mapRel );

		return analyseCoordinate( cd, mapRel , unitsToBeOrdered );

	}


	public static boolean analyseCoordinate( @NotNull CD cd, @NotNull vector mapRelCoord , @NotNull List<? extends LivingThing> thingsToCommand  ) {

		LivingThing enemy = EnemyAtLocationChecker.findEnemyHere( cd, mapRelCoord, thingsToCommand.get(0).getTeamName(), false);

		if( enemy != null )
		{
			//////Log.d( TAG , "enemy == " + enemy + ", setting highTreatTarget" );
			for( LivingThing lt : thingsToCommand )
			{
				lt.setHighThreadTarget(enemy);
				if( lt instanceof Humanoid )
					((Humanoid)lt).walkTo( null );
			}
			return true;
		}
		//////Log.d( TAG , "enemy == null at mapRelCoord" );

		return false;

	}

	@Override
	public void saveYourSelf(BufferedWriter b) throws IOException
	{


	}


	@NonNull
    @Override
	public OrderTypes getOrderType()
	{
		return OrderTypes.ATTACK_THIS;
	}

	private static final String name = "Attack";

	@NonNull
    @Override
	public String toString()
	{
		return name;
	}




}
