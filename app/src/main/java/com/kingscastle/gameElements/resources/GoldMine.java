package com.kingscastle.gameElements.resources;


import android.graphics.RectF;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;


public class GoldMine extends Mine
{
	private static final String TAG = "GoldMine";
	private static final String NAME = "Gold Mine";

	//private static RT resourceType = ;

	private static Image image;

	private static RectF staticPerceivedArea;



	@Override
	public int getMaxResources(){
		return 5000;
	}


	@Override
	public RT getResourceType(){
		return RT.GOLD;
	}



	@Override
	public Image getImage()
	{
		if( image == null )
			image = Assets.loadImage(R.drawable.gold_mine);

		return image;
	}

	@Override
	public RectF getStaticPerceivedArea()
	{
		if( staticPerceivedArea == null )
			staticPerceivedArea = Rpg.fourByFourArea;
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
