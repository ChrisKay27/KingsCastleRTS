package com.kingscastle.gameElements.resources;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.kingscastle.effects.animations.Anim;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.GameElement;
import com.kingscastle.gameElements.ImageFormatInfo;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import java.io.BufferedWriter;
import java.io.IOException;


public abstract class Mine extends GameElement implements Workable
{
	private int borderColor = Color.YELLOW;

	private Anim anim ;

	private int remainingResources = getMaxResources();


	private boolean alreadyAddedAnim;


	public Mine(){
	}

	@Override
	public int removeResources( int amount )
	{
		if( remainingResources >= amount )
		{
			remainingResources -= amount;
			return amount;
		}
		else
		{
			int amountToReturn = remainingResources;
			remainingResources = 0 ;
			allResourcesAreGone();

			return amountToReturn;
		}
	}

	private void allResourcesAreGone(){
		die();
	}

	@Override
	public int getRemainingResources(){
		return remainingResources;
	}



	@Override
	public boolean create( MM mm )
	{
		if( created ) return false;

		if( !alreadyAddedAnim )
		{
			loadAnim(mm);
			mm.getEm().add( getAnim() , true );
			alreadyAddedAnim = true;
		}
		updateArea();
		created = true;
		return true;
	}




	@Override
	public void loadAnim( MM mm )
	{
		if ( anim == null )
		{
			final Mine thisMine = this;
			anim = new Anim( getImage() ){

				private boolean incAlpha = true;
				private float sat = 1;
				private final ColorMatrix cm;
				{
					cm = new ColorMatrix();
					cm.setSaturation(sat);
                    Paint paint = new Paint();
					paint.setColorFilter(new ColorMatrixColorFilter(cm));
					paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
                    setPaint(paint);
				}
				@Override
				public int getSelectedBorderColor(){
					return thisMine.borderColor;
				}
				@Override
				public void paint( Graphics g , vector v ){
					super.paint( g , v );
					if( thisMine.isSelected() ){
						if( sat >= 1 )
							incAlpha = false;
						else if( sat <= 0 )
							incAlpha = true;

						if( incAlpha )
							sat += 0.07f;
						else
							sat -= 0.07f;
						cm.setSaturation(sat);
						getPaint().setColorFilter(new ColorMatrixColorFilter(cm));
					}
					else if( sat != 1){
						sat = 1;
						cm.setSaturation(sat);
                        getPaint().setColorFilter(new ColorMatrixColorFilter(cm));
					}
				}
			};
			anim.shouldBeDrawnThroughFog = true;
			anim.setLoc( getLoc() );
			//anim.setPaint( dstOverPaint );
		}
	}


	@Override
	public void setSelected(boolean b){
		super.setSelected(b);
		//getAnim().shouldDrawBorder = b;
	}



	public void setRemainingResources(int remainingResources) {
		this.remainingResources = remainingResources;
	}

	public Anim getAnim()
	{
		return anim;
	}

	public void setAnim(Anim anim)
	{
		this.anim = anim;
	}





	@Override
	public ImageFormatInfo getImageFormatInfo() {
		return null;
	}



	@Override
	public Image[] getStaticImages() {
		return null;
	}



	@Override
	public void setStaticImages(Image[] images)
	{
	}



	@Override
	public boolean isDone()
	{
		if( remainingResources <= 0 )
			allResourcesAreGone();

		return remainingResources <= 0 ;
	}



	@Override
	public boolean isABuilding()
	{
		return false ;
	}





	@Override
	public void die(){
		if( anim != null ){
			anim.setOver( true );
		}
		setDead( true );
	}




	public void setSelectedColor(int color)
	{
		borderColor = color;
	}

	@Override
	public RT getResourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getIconImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxResources() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RectF getStaticPerceivedArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}




}

