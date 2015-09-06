package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class RecoveryEffect extends Anim
{

	@NonNull
    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.recovery_effect_large, 5, 6);
	private final int staticTfb = 50;


	public RecoveryEffect( @NonNull vector loc )	{
        super(loc);
		setImages( staticImages );
		setTbf( staticTfb );
		setPaint( Rpg.getXferAddPaint() );
		onlyShowIfOnScreen = true;
	}

	void loadImages(){
		setImages( staticImages );
	}

	@Override
	public void paint( @NonNull Graphics g , @NonNull vector v )
	{
		vTemp.set( v );
		vTemp.add( offs );

		Image image = getImage();
		if( image != null ) {
			if (scaleX != 1 || scaleY != 1) {
				if (pScaleX != scaleX || pScaleY != scaleY) {
					dst.set(image.getSrcRect());
					dst.inset((int) (-(dst.width() / 2) * (scaleX - 1)), (int) (-(dst.height() / 2) * (scaleY - 1)));
				}
				dst.offsetTo(0, 0);
				dst.offset((int) vTemp.x - dst.width() / 2, (int) vTemp.y - dst.height() / 2);
				g.drawImage(image, image.getSrcRect(), dst, getPaint());
			} else
				g.drawImage(image, v.x - image.getWidthDiv2(), v.y - image.getHeightDiv2(), getPaint());
		}

	}
}
