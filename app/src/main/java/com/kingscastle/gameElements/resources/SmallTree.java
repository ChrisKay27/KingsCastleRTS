package com.kingscastle.gameElements.resources;


import android.graphics.RectF;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.framework.Settings;

import java.io.BufferedWriter;
import java.io.IOException;


public class SmallTree extends Tree
{

	private static final String TAG = "SmallTree";
	private static final String NAME = "Young Pine Tree";

	//private static final Image iconImage = Assets.loadImage( R.drawable.small_tree_icon );



	private static Image image;

	private static RectF staticPerceivedArea;


	@Override
	public int getMaxResources()
	{
		return 100;
	}




	@Override
	public Image getImage()
	{
		if ( image == null )
		{
			image = Assets.loadImage(R.drawable.small_tree);
		}
		return image;
	}





	@Override
	public RectF getStaticPerceivedArea()
	{
		if(staticPerceivedArea == null )
		{
			float dp = Rpg.getDp();

			staticPerceivedArea = new RectF( -18.5f*dp , -5f*dp , 13.5f*dp  , 11f*dp );


			//			int heightDiv4 = getImage().getHeightDiv2() / 2 ;
			//			int widthDiv8 =  getImage().getWidthDiv2()/4;
			//			staticPerceivedArea = new RectF( -getImage().getWidthDiv2() + widthDiv8 , -heightDiv4 , getImage().getWidthDiv2() - (widthDiv8*2) , heightDiv4 );
			//staticPerceivedArea = new RectF(-getImage().getWidthDiv2() , -heightDiv4 , getImage().getWidthDiv2() , heightDiv4 );
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
