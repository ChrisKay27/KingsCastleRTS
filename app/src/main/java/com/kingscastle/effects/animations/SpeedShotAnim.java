package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class SpeedShotAnim extends Anim
{

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.haste_spell, 4, 1);
	private final int staticTfb = 50;



	public SpeedShotAnim( @NonNull vector loc )
	{
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}



}
