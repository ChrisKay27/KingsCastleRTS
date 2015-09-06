package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class LightningStrikeAnim extends Anim {

	@NonNull
    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.lightning2, 2, 2);
	private final int staticTfb=60;


	public LightningStrikeAnim(@NonNull vector loc){
        super( loc );
        setImages(staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}



}
