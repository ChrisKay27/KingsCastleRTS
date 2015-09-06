package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kaebe.kingscastle.R;
import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class AuraAnim extends Anim {

	@NonNull
    private static final List<Image> staticImages = Assets.loadAnimationImages(R.drawable.aura, 8, 4);
	//private static final Vector staticOffs = new Vector();
	private final int staticTfb = 30;


	public AuraAnim(@NonNull vector loc)	{
        super(loc);
		setImages( staticImages );
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
		//setOffs( staticOffs );
	}





}
