package com.kingscastle.gameElements;

import android.graphics.RectF;

import com.kingscastle.gameElements.livingThings.SoldierTypes.Healer;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MageSoldier;
import com.kingscastle.gameElements.livingThings.SoldierTypes.MeleeSoldier;
import com.kingscastle.gameElements.livingThings.SoldierTypes.RangedSoldier;
import com.kingscastle.gameElements.livingThings.SoldierTypes.Unit;
import com.kingscastle.gameElements.livingThings.buildings.Building;
import com.kingscastle.gameUtils.vector;

import java.util.ArrayList;
import java.util.List;

public class GameElementUtil {

	public static vector getAverageLoc(List<? extends GameElement> units) {

		ArrayList<vector> unitsVectors = new ArrayList<>();
		for (GameElement u : units)
			unitsVectors.add( u.loc );

		return vector.getAverage( unitsVectors );
	}


	public static vector getRandomVectorInside( GameElement ge ) {
		RectF area = ge.area;

		return new vector( area.left + Math.random()*area.width() , area.top + Math.random()*area.height() );
	}


	public static vector getAverageLoc(Building[] buildings, int size)	{

		ArrayList<vector> unitsVectors = new ArrayList<>();
		for( int i = 0 ; i < size ; ++i )
			unitsVectors.add( buildings[i].loc );


		return vector.getAverage( unitsVectors );
	}

	public static int numMelee(List<? extends GameElement> ges ) {
		int numMelee = 0;

		for(GameElement ge : ges)
			if( ge instanceof MeleeSoldier)
				++numMelee;

		return numMelee;
	}


	public static int numRanged( List<? extends GameElement> ges )	{
		int numRanged = 0;

		for(GameElement ge : ges)
			if( ge instanceof RangedSoldier)
				++numRanged;

		return numRanged;
	}


	public static int numMage( List<? extends GameElement> ges )	{
		int numMage = 0;

		for(GameElement ge : ges)
			if( ge instanceof MageSoldier)
				++numMage;

		return numMage;
	}

	public static int numHealer( List<? extends GameElement> ges )	{
		int count = 0;

		for(GameElement ge : ges)
			if( ge instanceof Healer)
				++count;

		return count;
	}


	public static List<? extends Unit> getUnits( List<? extends GameElement> ges ){

		List<Unit> lts = new ArrayList<>();

		for( GameElement ge : ges )
			if( ge instanceof Unit ){
				Unit lt = (Unit) ge;
				lts.add( lt );
			}

		return lts;
	}





}
