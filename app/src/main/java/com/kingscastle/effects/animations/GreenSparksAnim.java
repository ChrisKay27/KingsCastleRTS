package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class GreenSparksAnim extends Anim {

	private static final List<Image> staticImages = null; //Assets.loadAnimationImages( R.drawable.sonic_punch , 5 , 5 );
	private final int staticTfb = 50;


    public GreenSparksAnim(@NonNull vector loc ) {
        super( loc );
		setImages( staticImages );
		setTbf( staticTfb );
		setPaint( Rpg.getXferAddPaint() );
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
