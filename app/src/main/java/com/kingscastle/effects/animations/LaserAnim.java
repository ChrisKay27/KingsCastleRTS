package com.kingscastle.effects.animations;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.CoordConverter;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class LaserAnim extends Anim
{
	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.small_laser_strike, 4, 4);
	private final int staticTfb = 50;

	private final vector to;
	private vector temp = new vector();

	public LaserAnim( @NonNull vector from , vector to )
	{
		setImages( staticImages );
		this.to = to;
		setLoc( from );
		setTbf( staticTfb );
		setPaint( new Paint() );
		setAliveTime( 1000 );
		setLooping( true );
		onlyShowIfOnScreen = true;
	}



	@Override
	public void paint( @NonNull Graphics g , @NonNull vector v , @NonNull CoordConverter cc )
	{
		temp = cc.getCoordsMapToScreen( to , temp );

		g.drawLine( v.x + offs.x , v.y + offs.y , temp.x , temp.y , Color.RED , 2 );

		if( addedBehind != null )
			for(int i = addedBehind.size() - 1 ; i > -1 ; --i )
				addedBehind.get( i ).paint( g , v );


		Image image = getImage();
		if( image != null )
		{
			g.drawImage( image , v.x    - image.getWidthDiv2() , v.y    - image.getHeightDiv2() , offs , getPaint() );
			g.drawImage( image , temp.x - image.getWidthDiv2() , temp.y - image.getHeightDiv2() , getPaint() );
		}

		if( addedInFront != null )
			for( int i = addedInFront.size() - 1 ; i > -1 ; --i )
				addedInFront.get(i).paint( g , v );

	}
}
