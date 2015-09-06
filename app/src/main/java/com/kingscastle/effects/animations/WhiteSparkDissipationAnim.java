package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameUtils.vector;

import java.util.List;


public class WhiteSparkDissipationAnim extends Anim
{

	private static final List<Image> staticImages= Assets.loadAnimationImages(R.drawable.effect_white_spark_dissipation, 5, 5);

	private final int staticTfb = 10;


	public WhiteSparkDissipationAnim(@NonNull vector loc)	{
		super(loc);
		setTbf(staticTfb);
		setImages(staticImages);
		setPaint( Rpg.getXferAddPaint() );
		onlyShowIfOnScreen = true;
	}



	@Override
	public void paint(@NonNull Graphics g, @NonNull vector v)	{
		Image image = getImage();

		if( image != null )
			g.drawImage( image , v.x - image.getWidthDiv2() , v.y - image.getHeightDiv2() , getPaint() );

	}

}
