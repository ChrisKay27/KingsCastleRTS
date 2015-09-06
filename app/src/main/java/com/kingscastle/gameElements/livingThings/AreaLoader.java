package com.kingscastle.gameElements.livingThings;

import android.graphics.Rect;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Image;


class AreaLoader
{

	@Nullable
    public Rect getAreaFromImage( @Nullable Image image , @Nullable Rect intoThis )
	{
		if ( image == null )
		{
			throw new IllegalArgumentException( " image == null "); 
		}
		if ( intoThis == null )
		{
			throw new IllegalArgumentException( " intoThis == null "); 
		}
		return intoThis;
	}

}
