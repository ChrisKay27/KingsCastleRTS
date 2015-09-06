package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

class Smoke2Anim extends Anim {

	private static final List<Image> staticImages = null;
	private final int staticTfb = 50;

	public Smoke2Anim(@NonNull vector loc) {
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		setLooping(true);
		onlyShowIfOnScreen = true;
	}


		//	images = Assets.loadAnimationImages(R.drawable.fire2f, 8, 5);
		//ArrayList<Image> images2 = Assets.loadAnimationImages(
		//		R.drawable.fire2b, 8, 5);
		//for (int i = images2.size() - 1; i >= 0; i--) {
		//	images.add(images2.get(i));
		//}



	@NonNull
    @Override
	public String toString() {
		return "SmokeType2";
	}
}
