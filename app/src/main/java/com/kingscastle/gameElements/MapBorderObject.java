package com.kingscastle.gameElements;

import android.graphics.RectF;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.managment.MM;

public class MapBorderObject extends GameElement
{

	{
		created = true;
	}

	@Override
	public boolean act()
	{
		return false;
	}



	@Override
	public boolean create(MM mm)
	{
		return true;
	}

	public MapBorderObject(RectF sideBorder )
	{
		area.set( sideBorder );  //CHANGED jan 12
	}




	@Nullable
    @Override
	public RectF getStaticPerceivedArea()
	{

		return null;
	}




	@Nullable
    @Override
	public ImageFormatInfo getImageFormatInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Nullable
    @Override
	public Image[] getStaticImages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStaticImages(Image[] images) {
		// TODO Auto-generated method stub

	}


}
