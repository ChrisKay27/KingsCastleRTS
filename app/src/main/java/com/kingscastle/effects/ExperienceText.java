package com.kingscastle.effects;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.kingscastle.gameElements.livingThings.LivingThing;


class ExperienceText extends RisingText {
	
	private final static int txtColor = Color.YELLOW;
	
	public ExperienceText(String t, @NonNull LivingThing on) {
		super(t, on);
		setColor(txtColor);
	}

	
	
	
	
}
