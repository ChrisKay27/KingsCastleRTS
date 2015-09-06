package com.kingscastle.gameElements.resources;


import android.graphics.RectF;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;

import java.io.BufferedWriter;
import java.io.IOException;


public class LargeTree extends Tree
{
	private static final String TAG = "LargeTree";
	private static final String NAME = "Birch Tree";

	//private static final Image iconImage = Assets.loadImage( R.drawable.large_tree_icon );
	private static Image image;

	private static RectF staticPerceivedArea;


	@Override
	public int getMaxResources()
	{
		return 400;
	}



	@Override
	public Image getImage()
	{
		if ( image == null )
		{
			image = Assets.loadImage(R.drawable.large_tree);
		}
		return image;
	}




	@Override
	public RectF getStaticPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			//int heightDiv4 = getImage().getHeightDiv2() / 2 ;
			//int widthDiv8 =  getImage().getWidthDiv2()/4;

			float dp = Rpg.getDp();

			staticPerceivedArea = new RectF( -26.5f*dp , -9f*dp , 21.5f*dp  , 23f*dp );

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
