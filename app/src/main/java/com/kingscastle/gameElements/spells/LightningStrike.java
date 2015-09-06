package com.kingscastle.gameElements.spells;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.LightningStrikeAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.livingThings.abilities.Ability;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;


public class LightningStrike extends InstantSpell{



	private static Image iconImage;

	private static RectF staticPerceivedArea;



    @NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.LIGHTNINGSTRIKE ; 			}


	@Override
	public boolean cast( @NonNull MM mm )
	{
		loadAnimation();
		mm.getEm().add(getAnim(),true);
		return true;
	}


	@Override
	public int calculateDamage()
	{
		return 30 + getCaster().getLQ().getLevel() * 3;
	}

	@Override
	public int calculateManaCost( @NotNull @NonNull LivingThing aWizard)
	{
		return 20 + aWizard.getLQ().getLevel() * 3;
	}




	@Override
	public RectF getPerceivedArea()
	{
		if (staticPerceivedArea == null )
		{
			int sizeDiv2 = (int) (30 * Rpg.getDp());
			staticPerceivedArea = new RectF(-sizeDiv2, -sizeDiv2, sizeDiv2, sizeDiv2);
		}
		return staticPerceivedArea;
	}



	@Override
	public boolean hitsOnlyOneThing() {
		return false;
	}




    @NonNull
    @Override
	public String toString() {
		return "Lightning Strike";
	}


    @NonNull
    @Override
	public String getName() {
		return "LightningStrike";
	}




	@Override
	public void loadAnimation()
	{
		if( getAnim() == null)
		{
			setAnim( new LightningStrikeAnim(loc) );
		}
	}



    @NonNull
    @Override
	public Spell newInstance()	{
		return new LightningStrike();
	}

	@NonNull
    @Override
	public Ability newInstance(@NotNull LivingThing target) {
		return new LightningStrike();
	}

	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.lightning_icon);
		}
		return iconImage;
	}




}
