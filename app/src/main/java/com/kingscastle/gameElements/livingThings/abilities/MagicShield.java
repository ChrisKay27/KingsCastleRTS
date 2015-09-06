package com.kingscastle.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.MagicShieldAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public class MagicShield extends Buff{

	private static Image iconImage;
	private int healBonus=3;

	{
		setAliveTime(10000);
		setRefreshEvery(1000);
	}

	public MagicShield(@NotNull LivingThing caster, @NotNull LivingThing target) {
		super(caster, target);
	}



	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.MAGICSHIELD ; 			}


	@Override
	public void undoAbility() {
	}
	@Override
	public void doAbility() {
	}




	@Override
	public void refresh(  EffectsManager em )
	{
		getTarget().takeHealing(healBonus);
	}

    @Override
    public boolean isOver() {
        return isDead();
    }


    public int calculateHealBonus()
	{
		if( getCaster() != null )
		{
			return 3 *  getCaster().getLQ().getLevel() * 2 ;
		}

		return 5;
	}



	@Override
	public int calculateManaCost(@NotNull @NonNull LivingThing aWizard)
	{
		if( aWizard != null )
		{
			return 10 *  aWizard.getLQ().getLevel() * 5 ;
		}

		return 15;
	}



	@NonNull
    @Override
	public String toString() {
		return "Magic Shield";
	}

	@NonNull
    public String getName() {
		return "MagicShield";
	}






	@Override
	public void loadAnimation( @NonNull MM mm )
	{
		if( getAnim() != null )
		{
			setAnim(new MagicShieldAnim(getTarget().loc));
			getAnim().setLooping(true);
		}
	}



	@NonNull
    @Override
	public Ability newInstance(@NotNull LivingThing target)	{
		return new MagicShield(getCaster(),target);
	}


	void loadIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.magic_shield_icon);
		}
	}



	@Override
	public Image getIconImage()
	{
		loadIconImage();
		return iconImage;
	}



	/**
	 * @return the healBonus
	 */
	public int getHealBonus() {
		return healBonus;
	}



	/**
	 * @param healBonus the healBonus to set
	 */
	public void setHealBonus(int healBonus) {
		this.healBonus = healBonus;
	}



	/**
	 * @param iconImage the iconImage to set
	 */
	public static void setIconImage(Image iconImage) {
		MagicShield.iconImage = iconImage;
	}









}
