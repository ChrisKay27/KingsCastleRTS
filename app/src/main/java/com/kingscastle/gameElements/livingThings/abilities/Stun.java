package com.kingscastle.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.AuraAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public class Stun extends Buff{

	private static Image iconImage;

	{
		setAliveTime(3000);
	}


	public Stun(@NotNull LivingThing caster, @NotNull LivingThing target) {
		super(caster, target);
	}

    @Override
    public boolean isOver() {
        return isDead();
    }


    @NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.STUN ; 			}


    @Override
    public void doAbility(){
        getTarget().getBonuses().subtractFromSpeedBonusMultiplier(1);
    }


	@Override
	public void undoAbility()	{
		getTarget().getBonuses().addToSpeedBonusMultiplier(1);
	}






	@Override
	public int calculateManaCost(@NotNull @NonNull LivingThing aWizard)
	{
		if(aWizard != null )
			return 25 + aWizard.getLQ().getLevel() * 7;
		return 25;
	}

	@NonNull
    @Override
	public Ability newInstance(@NotNull LivingThing target) {
		return new Stun(getCaster(),target);
	}


	@Override
	public void loadAnimation( @NonNull MM mm )
	{
		if( getAnim() == null)
			setAnim(new AuraAnim(getTarget().loc));
	}



	@NonNull
    public String getName()	{
		return "Stun";
	}



	@Override
	public Image getIconImage()	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.lightning_icon);
		}
		return iconImage;
	}







}
