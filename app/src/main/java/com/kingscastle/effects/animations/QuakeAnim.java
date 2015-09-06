package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuakeAnim extends Anim {
	public enum QuakeColor {Blue, Brown, Grey}

	@NonNull
    private static final List<Image> staticImagesGrey = Assets.loadAnimationImages(R.drawable.quake_large, 6, 3, 0, 6);
	@NonNull
    private static final List<Image> staticImagesBrown = Assets.loadAnimationImages(R.drawable.quake_large, 6, 3, 1, 6);
	@NonNull
    private static final List<Image> staticImagesBlue = Assets.loadAnimationImages(R.drawable.quake_large, 6, 3, 2, 6);

	private final int staticTfb = 60;

	public QuakeAnim(@NotNull vector loc , @NonNull QuakeColor color) {
        super(loc);
		switch( color ){
			case Blue: setImages(staticImagesBlue); break;
			case Brown: setImages(staticImagesBlue); break;
			case Grey: setImages(staticImagesBlue); break;
		}

		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}

	public QuakeAnim(@NotNull vector loc) {
        super(loc);
		setImages(staticImagesBlue);
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}

}
