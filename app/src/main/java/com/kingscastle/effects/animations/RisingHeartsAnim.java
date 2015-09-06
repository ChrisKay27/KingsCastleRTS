package com.kingscastle.effects.animations;

import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class RisingHeartsAnim extends Anim
{

	private static final List<Image> staticImages = null; 	//staticImages = Assets.loadAnimationImages()R.drawable.rising_hearts, 3, 3);
	private final int staticTfb = 100;


	public RisingHeartsAnim( @NonNull vector loc )	{
        super(loc);
        setImages( staticImages );
		setTbf( staticTfb );
		setPaint( new Paint() );
		onlyShowIfOnScreen = true;
	}




	@Override
	public void paint( @NonNull Graphics g , @NonNull vector v )
	{
		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , getPaint() );

	}
}
