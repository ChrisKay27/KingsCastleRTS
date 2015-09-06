package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class QuickBurstAnim extends Anim {

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.quick_burst, 5, 2);
	private final int staticTfb = 80;

	public QuickBurstAnim(@NonNull vector loc) {
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		setPaint( Rpg.getXferAddPaint() );
		onlyShowIfOnScreen = true;
	}



}
