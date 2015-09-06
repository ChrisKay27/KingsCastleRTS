package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Graphics;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.ArrayList;

public class FlashingAnim extends Anim {

	private final int staticTfb = 300;
	private long lastChangedAt;
	private boolean on=true;


	public FlashingAnim(@NonNull vector loc,Image img,int aliveTime) {
        super( loc );
		ArrayList<Image> imgs = new ArrayList<>(1);
        imgs.add(img);
        setImages(imgs);
        setAliveTime(aliveTime);
		onlyShowIfOnScreen = true;
	}

	@Override
	public void paint(@NonNull Graphics g, @NonNull vector v) {
		if( on )
			super.paint(g,v);

		if (lastChangedAt + staticTfb < GameTime.getTime()) {
			on = !on;
			lastChangedAt=GameTime.getTime();
		}
	}

}
