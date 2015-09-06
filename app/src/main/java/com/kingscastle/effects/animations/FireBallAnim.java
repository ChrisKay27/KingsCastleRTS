package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class FireBallAnim extends Anim
{

	private static final List<Image> Wimages, NWimages, Nimages,
	NEimages, Eimages, SEimages, Simages, SWimages;

	private final static int staticTfb = 50;
	private final static int imageId = R.drawable.fireballs;

	static
	{
		Image fireballs = Assets.loadImage(imageId);
		Wimages = Assets.loadAnimationImages( fireballs, 8, 8, 0, 8 , false );
		NWimages = Assets.loadAnimationImages( fireballs, 8, 8, 1, 8 , false );
		Nimages = Assets.loadAnimationImages( fireballs, 8, 8, 2, 8 , false );
		NEimages = Assets.loadAnimationImages( fireballs , 8, 8, 3, 8 , false );
		Eimages = Assets.loadAnimationImages( fireballs , 8, 8, 4, 8 , false );
		SEimages = Assets.loadAnimationImages( fireballs , 8, 8, 5, 8 , false );
		Simages = Assets.loadAnimationImages( fireballs , 8, 8, 6, 8 , false );
		SWimages = Assets.loadAnimationImages( fireballs , 8, 8, 7, 8 , true );
	}


	public FireBallAnim(@NonNull vector loc , int dir){
        super(loc);
		setImages( getImages(dir) );
		setTbf(staticTfb);
		setLooping(true);
		onlyShowIfOnScreen = true;

		LightEffect le = new LightEffect(this.loc, LightEffect.LightEffectColor.DARK_ORANGE);
		le.setScale(0.5f);
		add(le, true);
	}



	List<Image> getImages(int dir){
		switch (dir) {
		default:
		case 0:
			return Wimages;
		case 1:
			return NWimages;
		case 2:
			return Nimages;
		case 3:
			return NEimages;
		case 4:
			return Eimages;
		case 5:
			return SEimages;
		case 6:
			return Simages;
		case 7:
			return SWimages;
		}
	}
}