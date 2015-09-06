package com.kingscastle.ui.buttons;

import android.app.Activity;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.kingscastle.framework.implementation.ImageDrawable;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.ui.AbilityCaster;


public class AbilityButton extends SButton
{
	private Ability ab;


	private AbilityButton( Activity a, @NonNull Ability ability )
	{
		super(a);

		if( ability.getIconImage() != null ){
			ImageDrawable id = new ImageDrawable( ability.getIconImage().getBitmap() , 0 , 0 , new Paint());
			setBackgroundDrawable(id);
		}

		setOnClickListener( new OnClickListener(){
			@Override
			public void onClick(View v) {
				AbilityCaster.getInstance().setPendingAbility( ab );
			}
		});
	}


	public Ability getAbility() {
		return ab;
	}



	@Nullable
    public static AbilityButton getInstance( Activity a , @Nullable Ability ability )
	{
		if( ability == null )
			throw new IllegalArgumentException("Trying to set ability of an abilityButton and ability was null.");

		AbilityButton ab = new AbilityButton( a , ability );
		return ab;
	}








	@NonNull
    @Override
	public AbilityButton clone(){
		return new AbilityButton( a , ab );
	}









}
