package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class DyingAnim extends Anim {

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.genericexplodingperson, 3);
    static{staticImages.add( Assets.loadImage( R.drawable.holy_corpse ) );}
	private final int staticTfb = 150;


	public DyingAnim(@NonNull vector loc) {
        super( loc );
		setTbf(staticTfb);
		setAliveTime( 30000 );
		onlyShowIfOnScreen = true;
	}

	@Override
	public Image getImage()	{
		return staticImages.get( currentImageIndex );
	}


	@Override
	public boolean act() {
		if( startTime + aliveTime < GameTime.getTime() )
			over = true;

		if( currentImageIndex == staticImages.size()-1 )		{
		}
		else if( nextImageChange + tbf < GameTime.getTime() )		{
			++currentImageIndex;
			nextImageChange = GameTime.getTime();;
		}
		return over;
	}


}
