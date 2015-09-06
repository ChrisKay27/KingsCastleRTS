package com.kingscastle.gameElements.livingThings.attacks;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.animations.BlackSummonSmokeAnim;
import com.kingscastle.effects.animations.HealSparklesAnim;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.gameElements.managment.MM;

public class HealingAttack extends Attack
{

	private final Ability healingAbility;
	private boolean showEvilAnimation = false;

	public HealingAttack( @NonNull MM mm, @NonNull LivingThing caster , Ability healingAbility2 )
	{
		super( mm, caster );
		healingAbility = healingAbility2;
	}


	@Override
	public boolean attack( @Nullable LivingThing target )
	{
		if( target == null || healingAbility == null )
		{
			//System.out.println("HealingAttack told to act when target:" + target + " and owner:" + owner + " and healingSpell:" + healingAbility);
			return false;
		}

		if( showEvilAnimation )
		{
			mm.getEm().add(
					new BlackSummonSmokeAnim(owner.loc),true);

		}
		else
		{
			mm.getEm().add(
					new HealSparklesAnim(owner.loc),true);
		}

		healingAbility.cast( mm , target );
		return true;
	}



	/**
	 * @return the evilAnimation
	 */
	public boolean isUsingEvilAnimation() {
		return showEvilAnimation;
	}


	/**
	 * @param evilAnimation the evilAnimation to set
	 */
	public void setShowEvilAnimation(boolean evilAnimation) {
		this.showEvilAnimation = evilAnimation;
	}


	@Override
	public void act() {
	}
}
