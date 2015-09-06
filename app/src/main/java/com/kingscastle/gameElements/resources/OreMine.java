package com.kingscastle.gameElements.resources;


import android.graphics.RectF;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Rpg;


public class OreMine extends Mine
{
	private static final String TAG = "OreMine";

	private static final RT resourceType = RT.METAL;

	private static final String NAME = "Ore Mine";

	private static Image image;

	private static RectF staticPerceivedArea;



	@Override
	public int getMaxResources()
	{
		return 5000;
	}



	@Override
	public RT getResourceType()
	{
		return resourceType;
	}





	@Override
	public Image getImage()
	{
		if ( image == null )
		{
			image = Assets.loadImage(R.drawable.ore_mine);
		}
		return image;
	}



	@Override
	public RectF getStaticPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			staticPerceivedArea = Rpg.fourByFourArea;
			//			staticPerceivedArea = new RectF(-getImage().getWidthDiv2() + Rpg.fiveDp , -getImage().getHeightDiv2()  + Rpg.fiveDp ,
			//					getImage().getWidthDiv2() - Rpg.fiveDp , getImage().getHeightDiv2() - Rpg.fiveDp );
		}
		return staticPerceivedArea;
	}




	@Override
	public String toString(){
		return NAME;
	}
	@Override
	public String getName(){
		return NAME;
	}



	@Override
	public Image getIconImage() {
		return null;
	}



}
