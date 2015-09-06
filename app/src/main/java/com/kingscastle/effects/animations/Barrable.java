package com.kingscastle.effects.animations;

import android.support.annotation.NonNull;

public interface Barrable
{
	public float getPercent();

	public int getMaxValue();

	public int getValue();

	@NonNull
    String getTimeToCompletion();
}
