package com.kingscastle.framework.implementation;

import android.view.View.OnTouchListener;

import com.kingscastle.framework.Input;

import java.util.List;

interface TouchHandler extends OnTouchListener {
	boolean isTouchDown(int pointer);

	int getTouchX(int pointer);

	int getTouchY(int pointer);

	List<Input.TouchEvent> getTouchEvents();

}
