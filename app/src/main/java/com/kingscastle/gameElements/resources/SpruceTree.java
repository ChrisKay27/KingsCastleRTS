package com.kingscastle.gameElements.resources;


import android.graphics.RectF;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;

import java.io.BufferedWriter;
import java.io.IOException;


public class SpruceTree extends Tree
{
	private static final String TAG = "SpruceTree";
	private static final String NAME = "Spruce Tree";

	//private static final Image iconImage = Assets.loadImage( R.drawable.spruce_tree_icon );

	private static Image image;

	private static RectF staticPerceivedArea;


	@Override
	public int getMaxResources(){
		return 300;
	}





	@Override
	public Image getImage()
	{
		if ( image == null )
			image = Assets.loadImage(R.drawable.spruce_tree);

		return image;
	}


	@Override
	public RectF getStaticPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{


			//int heightDiv4 = getImage().getHeightDiv2()/2 ;
			//int widthDiv8 =  getImage().getWidthDiv2()/4;

			float dp = Rpg.getDp();

			staticPerceivedArea = new RectF( -21*dp , -13f*dp , 11f*dp  , 19f*dp );

			//staticPerceivedArea = new RectF( -getImage().getWidthDiv2() + widthDiv8 , -heightDiv4 , getImage().getWidthDiv2() - (widthDiv8*2) , heightDiv4 );
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



	@Override
	public void saveYourself( BufferedWriter bw ) throws IOException
	{
		String temp;
		if( Settings.savingYourBase ){
			temp = "<S x=\"" + (int) (loc.x/Rpg.getDp()) + "\" y=\"" + (int) (loc.y/Rpg.getDp()) + "\" rr=\"" + remainingResources + "\" >";
		}else{
			temp = "<S x=\"" + (int) (loc.x) + "\" y=\"" + (int) (loc.y) + "\" rr=\"" + remainingResources + "\" >";
		}


		bw.write( temp , 0 , temp.length() );
		bw.newLine();


		temp = "</S>";
		bw.write( temp , 0 , temp.length() );
		bw.newLine();
	}

}
