package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class IlluminatedFog extends Anim
{
	public static final int SMALL = 0;
	public static final int LARGE = 1;

	@NonNull
    private static final List<Image> smallStaticImages = Assets.loadAnimationImages(R.drawable.illuminated_fog, 5, 4);
	@NonNull
    private static final List<Image> largeStaticImages = Assets.loadAnimationImages( R.drawable.illuminated_fog_large , 5 , 4 );
	private final int staticTfb = 50;



	public IlluminatedFog( @NonNull vector loc , int size ) {
        super( loc );

		if( size == LARGE )
			setImages( largeStaticImages );
		else
			setImages( smallStaticImages );


		setTbf( staticTfb );
		setPaint( Rpg.getXferAddPaint() );
		onlyShowIfOnScreen = true;
	}



	@Override
	public void paint( @NonNull Graphics g , @NonNull vector v ) {
		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , getPaint() );
	}
}
