package com.kingscastle.ui.buttons;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View.OnClickListener;


public interface Buttonable {

	@NonNull
    Drawable getDrawable();
	@NonNull
    OnClickListener getOnClickListener();

}
