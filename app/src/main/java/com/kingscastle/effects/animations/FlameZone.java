package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class FlameZone extends Anim
{

	@NonNull
    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.flame_zone, 5, 4);
	private final int staticTfb = 50;


	public FlameZone(@NonNull vector loc) {
        super( loc );

        setImages(staticImages );
		setTbf( staticTfb );
		setPaint( Rpg.getXferAddPaint() );
		onlyShowIfOnScreen = true;
	}

	void loadImages()
	{
		setImages( staticImages );
	}


	@Override
	public void paint( @NonNull Graphics g , @NonNull vector v )
	{
		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , getPaint() );

	}
}
