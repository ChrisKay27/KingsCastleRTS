package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class TorchLightAnim extends Anim
{

	@NonNull
    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.torchlight, 8, 8);
	private final int staticTfb = 35;


	public TorchLightAnim( @NonNull vector loc )	{
        super( loc );
        setImages(staticImages );

		setTbf( staticTfb );
		setPaint(Rpg.getXferAddPaint());
	}


	@Override
	public void paint( @NonNull Graphics g , @NonNull vector v )
	{
		Image image = getImage();
		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , getPaint() );
	}
}
