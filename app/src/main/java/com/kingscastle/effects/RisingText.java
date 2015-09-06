package com.kingscastle.effects;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameUtils.vector;


public class RisingText extends GameElement
{
	@NonNull
    private static final Paint redCenter;
	@NonNull
    private static final Paint greenCenter;
	private static final float dp;

	private String text;
	long dieAt = GameTime.getTime() + 1500;
	long nextMove = 0;
	private final int color = Color.RED;
	//private static Paint paint;
	private boolean visible = true;
	public boolean shouldDrawThis;

	private final Paint paint = new Paint();

	static
	{
		redCenter = new Paint();
		redCenter.setTextAlign( Align.CENTER );
		redCenter.setColor( Color.RED );
		redCenter.setTextSize( Rpg.getSmallestTextSize() );
		redCenter.setXfermode( new PorterDuffXfermode( Mode.SRC ));
		redCenter.setTypeface( Rpg.getImpact() );

		greenCenter = new Paint();
		greenCenter.setTextAlign( Align.CENTER );
		greenCenter.setColor( Color.GREEN );
		greenCenter.setTextSize( Rpg.getSmallestTextSize() );
		greenCenter.setXfermode( new PorterDuffXfermode( Mode.SRC ));
		greenCenter.setTypeface( Rpg.getImpact() );

		dp = Rpg.getDp();
	}


	{
		paint.setXfermode(new PorterDuffXfermode( Mode.SRC ));
		paint.setTextSize( Rpg.getTextSize() );
		paint.setColor(Color.WHITE);
		paint.setTextAlign( Align.CENTER );
		paint.setTypeface( Rpg.getImpact() );
	}


	public void reset()
	{
		dieAt = GameTime.getTime() + 1500;
		visible = true;
		dead = false;
		nextMove = 0;
	}


	public RisingText() {
	}

	RisingText(String t, @NonNull LivingThing on)
	{
		setText( t );
		setLoc( new vector( on.loc ) );
	}

	RisingText(String t, vector at)
	{
		setText(t);
		setLoc( new vector( at ) );
	}




	public void	incrimentGraphics()
	{
		if( nextMove < GameTime.getTime() )
		{
			nextMove = GameTime.getTime() + 100;
			loc.translate( 0 , -dp );
		}

		if( dieAt < GameTime.getTime() )
			setDead(true);
	}

	public int getWidthOfSprite() {return 0;}

	public int getHeightOfSprite() {return 0;}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public int getColor() {
		return paint.getColor();
	}

	public void setColor(int txtcolor) {
		paint.setColor(txtcolor);
		//color = txtcolor;
	}

	@Override
	public RectF getPerceivedArea() {
		return null;
	}


	public Paint getPaint()
	{
		//		if( color == Color.RED )
		//			return redCenter;
		//		else if( color == Color.GREEN )
		//			return greenCenter;

		return paint;
	}

	public void setTextSize(float size) {
		paint.setTextSize(size);
	}


	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return null;
	}




	@Override
	public RectF getStaticPerceivedArea() {
		return Rpg.getNormalPerceivedArea();
	}


	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
	}

	@Override
	public Image[] getStaticImages() {
		return null;
	}

	@Override
	public void setStaticImages(Image[] images) {
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible( boolean visible ) {
		this.visible = visible;
	}







}
