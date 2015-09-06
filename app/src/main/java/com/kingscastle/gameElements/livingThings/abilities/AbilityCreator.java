package com.kingscastle.gameElements.livingThings.abilities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AbilityCreator
{

	@Nullable
    public static Ability getAbility( @NonNull AbilityParams params )
	{
		return params.getAbilityToBeCopied().newInstance(params.getTarget());
	}
}
