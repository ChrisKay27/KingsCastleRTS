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


public abstract class Tree extends GameElement implements Workable {

	private int borderColor = Color.YELLOW;


	//private static RT resourceType;// = RT.WOOD;


	private Anim anim;

	protected int remainingResources = getMaxResources();


	@Override
	public abstract Image getImage();


	public Tree(){
	}


	@Override
	public int removeResources( int amount )
	{
		//////Log.i( TAG , "Trying to get " + amount + " of my wood." );
		if( remainingResources >= amount )
		{
			//	////Log.i( TAG , " I have enough wood since i have " + remainingResources + " wood." );
			remainingResources -= amount;
			//////Log.i( TAG , " Now i have " + remainingResources + " wood." );
			if( remainingResources == 0 )
			{
				allResourcesAreGone();
			}
			return amount;
		}
		else
		{
			//	////Log.i( TAG , " Now i have less then enough wood since i have " + remainingResources );

			int amountToReturn = remainingResources;

			remainingResources = 0 ;
			allResourcesAreGone();

			return amountToReturn;
		}
	}




	private void allResourcesAreGone()
	{
		//////Log.i( TAG , " All Resources are gone setting dead=true." );
		setDead( true );
		anim.setOver( true );
	}




	@Override
	public RT getResourceType()
	{
		//resourceType = RT.WOOD;
		return RT.WOOD;
	}




	@Override
	public int getRemainingResources()
	{
		//////Log.i( TAG , " Somethings checking out my remainingResources, i have left : " + remainingResources );
		if( remainingResources <= 0 )
			allResourcesAreGone();

		return remainingResources;
	}




	public void setRemainingResources(int remainingResources2)
	{
		remainingResources = remainingResources2;
	}




	@Override
	public boolean create( MM mm )
	{
		if ( !hasBeenCreated() )
		{
			loadAnim(mm);
			mm.getEm().add( anim , true );
			created = true ;
		}
		updateArea();
		return true;
	}

	@Override
	public void loadAnim( MM mm )
	{
		if( anim == null )
		{
			final Tree thisTree = this;
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
					return thisTree.borderColor;
				}
				@Override
				public void paint( Graphics g , vector v ){
					super.paint( g , v );
					if( thisTree.isSelected() ){
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
		}
		anim.shouldBeDrawnThroughFog = true;
		anim.setLoc( getLoc() );
		//anim.setPaint( dstATopPaint );
	}







	@Override
	public void setSelected(boolean b){
		super.setSelected(b);
		getAnim().shouldDrawBorder = b;
	}

	public Anim getAnim(){
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
	public void setStaticImages(Image[] images) {
	}




	public static Tree getARandomTree()
	{
		double random = Math.random();

		if ( random < 0.4 )
		{
			return new SpruceTree();
		}
		else if ( random < 0.7 )
		{
			return new SmallTree();
		}
		else
		{
			return new LargeTree();
		}

	}




	@Override
	public boolean isDead(){
		return isDone();
	}

	@Override
	public void die(){
		if( anim != null ){
			anim.setOver( true );
		}
		setDead( true );
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
	public void saveYourself( BufferedWriter bw ) throws IOException
	{
		String temp;
		if( Settings.savingYourBase ){
			temp = "<" + getClass().getSimpleName() + " x=\"" + (int) (loc.x/ Rpg.getDp()) + "\" y=\"" + (int) (loc.y/Rpg.getDp()) + "\" rr=\"" + remainingResources + "\" >";
		}else{
			temp = "<" + getClass().getSimpleName() + " x=\"" + (int) (loc.x) + "\" y=\"" + (int) (loc.y) + "\" rr=\"" + remainingResources + "\" >";
		}


		bw.write( temp , 0 , temp.length() );
		bw.newLine();


		temp = "</" + getClass().getSimpleName() + ">";
		bw.write( temp , 0 , temp.length() );
		bw.newLine();
	}



	public void setSelectedColor(int color)
	{
		borderColor = color;
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

}
