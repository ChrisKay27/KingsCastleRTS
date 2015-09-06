package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class Fire1Anim extends Anim
{

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.smaller_fire, 15, 5);//,images1,images2,images3,images4;
	private final int staticTfb = 50;
	private final int imageNum = 0;

	public Fire1Anim( @NonNull vector loc )
	{
        super(loc);
		setImages( staticImages );
		setTbf( staticTfb );
		setLooping( true );
		onlyShowIfOnScreen = true;
	}

	//	public Fire1Anim( Vector loc , int i )
	//	{
	//		this( loc );
	//
	//		setImages( staticImages );
	//		imageNum = i;
	//		switch( i )
	//		{
	//		case 0:
	//			setImages( images ); break;
	//		case 1:
	//			setImages( images1 ); break;
	//		case 2:
	//			setImages( images2 ); break;
	//		case 3:
	//			setImages( images3 ); break;
	//		case 4:
	//			setImages( images4 ); break;
	//		}
	//
	//		setLoc( loc );
	//		setTbf( staticTfb );
	//		setLooping( true );
	//	}



	@Override
	public void paint( @NonNull Graphics g , @NonNull vector v )
	{
		super.paint(g, v);


		//	g.drawString( imageNum + "", v.x, v.y + Rpg.thirtyDp , paint );

		//g.drawString( currentImageIndex +"", v.x, v.y + 2*Rpg.thirtyDp , paint );
	}


	@NonNull
    @Override
	public String toString()
	{
		return "FireType1";
	}
}
