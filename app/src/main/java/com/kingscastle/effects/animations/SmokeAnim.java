package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class SmokeAnim extends Anim {

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.smoke_loop, 12, 3);
	private final int staticTfb = 50;

	public SmokeAnim(vector loc) {
        super(loc);
		setImages( staticImages );
		tbf = staticTfb;
		onlyShowIfOnScreen = true;
	}


	@NonNull
    @Override
	public String toString() {
		return "SmokeType1";
	}
}
