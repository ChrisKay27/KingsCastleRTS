package com.kingscastle.effects;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;


public class DamageText extends RisingText
{

	@NonNull
    private static final Paint redPaint;

	static
	{
		redPaint = new Paint();
		redPaint.setTextSize( Rpg.getSmallestTextSize() );
		redPaint.setTextAlign( Paint.Align.CENTER );
		redPaint.setAntiAlias( true );
		redPaint.setColor( Color.RED );
	}

	public DamageText( String t , LivingThing on )
	{
		super( t , on );
	}



	@NonNull
    @Override
	public Paint getPaint()
	{
		return redPaint;
	}




}
