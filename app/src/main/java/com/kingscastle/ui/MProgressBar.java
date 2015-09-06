package com.kingscastle.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class MProgressBar extends ProgressBar{

	public MProgressBar(@NonNull Context context) {
		super(context);
	}
	public MProgressBar(@NonNull Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MProgressBar(@NonNull Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}



	private final Handler mHandler = new Handler();





	/**
	 * Thread safe version if this ProgresBar was created on the UI Thread.
	 */
	@Override
	public void setProgress( final int mProgressStatus ){
		if( mHandler == null ) return;
		// Update the progress bar
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				MProgressBar.super.setProgress(mProgressStatus);
			}
		});
	}


	@Override
	protected boolean verifyDrawable(Drawable who) {
		return true;
	}

}
