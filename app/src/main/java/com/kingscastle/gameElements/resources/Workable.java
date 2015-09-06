package com.kingscastle.gameElements.resources;

import android.graphics.RectF;

import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;


public interface Workable {

	int getRemainingResources();
	int removeResources(int amount);
	RT getResourceType();
	vector getLoc();
	boolean isDone();
	boolean isABuilding();


	enum RT
	{
		GOLD , METAL , WOOD , FOOD , BUILDING , BUILDING_REPAIR ,  MAGIC_DUST , POP;// , REPAIR

		private String name;

		public static RT getFromString( String t )
		{
			if( t.equals( GOLD.toString() ) )
				return GOLD;
			else if( t.equals( METAL.toString() ) )
				return METAL;
			else if( t.equals( WOOD.toString() ) )
				return WOOD;
			else if( t.equals( FOOD.toString() ) )
				return FOOD;
			else if( t.equals( BUILDING.toString() ) )
				return BUILDING;

			//			else if( t.equals( REPAIR.toString() ) )
			//
			//				return REPAIR;
			//
			else if( t.equals( BUILDING_REPAIR.toString() ) )
				return BUILDING_REPAIR;
			else
				return null;
		}

		@Override
		public String toString(){
			if( name != null )
				return name;
			else{
				String n = name().toLowerCase();//Locale.CANADA);
				name = n.substring(0,1).toUpperCase() + n.substring(1);
				return name;
			}
		}
	}



	RectF getArea();
	Image getIconImage();
	Image getImage();
	RectF getPerceivedArea();
	void updateArea();
	RectF getStaticPerceivedArea();
	void setSelected(boolean b);
	int getMaxResources();
	boolean isDead();

}
