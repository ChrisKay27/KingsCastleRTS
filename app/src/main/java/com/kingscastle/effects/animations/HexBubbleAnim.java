package com.kingscastle.effects.animations;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class HexBubbleAnim extends Anim {

	private static final List<Image> staticBlueImages = Assets.loadAnimationImages(R.drawable.hex_bubble, 4, 4);;
	private static final List<Image> staticWhiteImages = Assets.loadAnimationImages( R.drawable.hex_shield_white , 4 , 4 );;

	private final int staticTfb = 50;

	{
		onlyShowIfOnScreen = true;
	}

	public HexBubbleAnim(@NonNull vector loc)	{
        super(loc);
        setImages(getImages(Color.BLUE ) );
		setTbf(staticTfb);
		offs.add( Rpg.getDp() , Rpg.getDp() );
	}

	public HexBubbleAnim(@NonNull vector loc , int color )	{
        super(loc);
        setImages(getImages( color ));

		setTbf(staticTfb);
		offs.add( Rpg.getDp() , Rpg.getDp() );
	}




	private List<Image> getImages(int color)
	{
		switch( color )
		{
		default:
		case Color.BLUE:
			return staticBlueImages;
		case Color.WHITE:
			return staticWhiteImages;
		}
	}




}
