package com.kingscastle.effects.animations;


import android.support.annotation.NonNull;

import com.kingscastle.framework.Assets;
import com.kingscastle.framework.Image;
import com.kaebe.kingscastle.R;
import com.kingscastle.gameUtils.vector;

import java.util.List;

public class Flames extends Anim {

	@NonNull
    private static final List<Image> staticSImages = Assets.convToArrayList(Assets.loadImages(R.drawable.flames,3,1,0,0,1,4));
	@NonNull
    private static final List<Image> staticWImages = Assets.convToArrayList(Assets.loadImages(R.drawable.flames, 3, 1, 0, 1, 1, 4));
	@NonNull
    private static final List<Image> staticEImages = Assets.convToArrayList(Assets.loadImages(R.drawable.flames,3,1,0,2,1,4));
	@NonNull
    private static final List<Image> staticNImages = Assets.convToArrayList(Assets.loadImages(R.drawable.flames,3,1,0,3,1,4));

	private final int staticTfb = 100;

	/**
	 * North=0,East=1...
	 * @param loc
	 * @param dir
	 */
	public Flames(@NonNull vector loc , int dir){
        super(loc);
		switch( dir ){
		case 0:setImages(staticNImages); break;
		case 1:setImages(staticEImages); break;
		case 2:setImages(staticSImages); break;
		case 3:setImages(staticWImages); break;
		}
		setTbf(staticTfb);
		onlyShowIfOnScreen = true;
	}




}



