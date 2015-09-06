package com.kingscastle.ui.buttons;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.framework.Image;
import com.kingscastle.framework.implementation.ImageDrawable;


public class CancelButton extends SButton
{
	@Nullable
    private static final Image buttonIcon = null;//Assets.loadImage(R.drawable.stop_icon);
	protected static final ImageDrawable buttonIconDrawable = new ImageDrawable( buttonIcon.getBitmap() , new Paint());


	public CancelButton( Activity daveOsborn , OnClickListener ocl ){
		super(daveOsborn);
		setForeground(buttonIconDrawable);
		setOnClickListener(ocl);
	}


	@NonNull
    public static SButton getInstance(Activity a,
			OnClickListener ocl) {
		return new CancelButton( a , ocl );
	}

}
