package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.ArrayList;
import java.util.List;

public class FreezeAnim extends Anim {

	private static final String TAG = "FreezeAnim";

	private static final List<Image> images0 = Assets.loadAnimationImages(R.drawable.freeze, 4, 6, 0, 4);
	private static final List<Image> images1 = Assets.loadAnimationImages(R.drawable.freeze, 4, 6, 1, 4);
	private static final List<Image> images2 = Assets.loadAnimationImages(R.drawable.freeze, 4, 6, 2, 4);
	private static final List<List<Image>> staticImages = new ArrayList<>();
	static{
		staticImages.add(images0); staticImages.add(images1);
		staticImages.add(images2);
	}

	private final int staticTfb = 100;



	public FreezeAnim( @NonNull vector loc , int aliveTime )	{
        super(loc);
        ////Log.d( TAG , "FreezeAnim constructed, staticImages.size() = " + staticImages.size() );

		setImages(staticImages.get((int) (Math.random() * staticImages.size())));
        setTbf( staticTfb );
		setAliveTime( aliveTime );
		//setPaint(Rpg.getXferAddPaint());
	}



	@Override
	public boolean act()
	{
		if( nextImageChange < GameTime.getTime() )
		{
			nextImageChange = GameTime.getTime() + tbf ;

			if( currentImageIndex+1 == staticImages.size() )
			{
				if( startTime + aliveTime < GameTime.getTime() ){
					//Log.d( TAG , "Out of time , over = true");
					return over = true;}
				else if( isLooping() )
					currentImageIndex = 0;
				else{
					//Log.d( TAG , "Cycled through all staticImages holding at last one");
					return false;}
			}
			else
				++currentImageIndex;
		}
		return over;
	}

	@Override
	public Image getImage()
	{
		return getImages().get( currentImageIndex );
	}

	@NonNull
    public FreezeAnim newInstance(@NonNull vector v, int aliveTime){
		return new FreezeAnim(v,aliveTime);
	}

}
