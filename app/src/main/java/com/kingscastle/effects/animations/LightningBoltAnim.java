package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class LightningBoltAnim extends Anim {

	private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.lightning_bolt, 5, 4);
	private final int staticTfb=50;

	public LightningBoltAnim(@NonNull vector loc){
        super(loc);

        setImages(staticImages);
		setTbf(staticTfb);
		setLooping(true);
		onlyShowIfOnScreen = true;
		LightEffect le = new LightEffect(this.loc, LightEffect.LightEffectColor.LIGHT_ORANGE);
		le.setScale(0.5f);
		add(le, true);
	}

}
