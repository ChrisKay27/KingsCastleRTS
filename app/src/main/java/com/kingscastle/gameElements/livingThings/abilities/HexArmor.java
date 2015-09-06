package com.kingscastle.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.HexBubbleAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.Bonuses;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public class HexArmor extends Buff
{

	private final int armorBonus=10;

	private static Image iconImage;

	{
		setAliveTime(30000);
		setRefreshEvery(2000);
	}

	public HexArmor(@NotNull LivingThing caster, @NotNull LivingThing target) {
		super(caster, target);
	}

	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.HEXARMOR ; 			}

	@Override
	public void undoAbility()
	{
		Bonuses b = getTarget().getLQ().getBonuses();
		b.setArmorBonus(b.getArmorBonus()-armorBonus);

	}

	@Override
	public void doAbility()
	{
		Bonuses b = getTarget().getLQ().getBonuses();
		b.setArmorBonus(b.getArmorBonus()+armorBonus);
	}



	@Override
	protected void refresh(  @NonNull EffectsManager em )
	{
		Anim anim = getAnim();
		if( anim != null )
		{
			anim.restart();
			em.add(anim, EffectsManager.Position.InFront);
		}
	}

    @Override
    public boolean isOver() {
        return isDead();
    }


    @Override
	public int calculateManaCost(@NotNull @NonNull LivingThing aWizard)
	{
		if(aWizard != null )
			return 20 + aWizard.getLQ().getLevel() * 5;

		return 25;
	}

	@NonNull
    @Override
	public Ability newInstance(@NotNull LivingThing target)	{
		return new HexArmor(getCaster(),target);
	}


	@NonNull
    @Override
	public String toString()
	{
		return "Hex Armor";
	}
	@NonNull
    public String getName()
	{
		return "HexArmor";
	}





	@Override
	public void loadAnimation(@NonNull @NotNull MM mm )
	{
		Anim anim = getAnim();
		if( anim == null ){
			anim = new HexBubbleAnim(getTarget().loc);
			setAnim(anim);
			anim.setLooping(false);
		}
	}


	@Override
	public Image getIconImage()
	{
		loadIconImage();
		return iconImage;
	}



	private void loadIconImage()
	{
		if( iconImage == null)
		{
			throw new IllegalStateException("There is no image for this animation.");
			//iconImage = Assets.loadImage( R.drawable.hex_armor_icon );
		}

	}








}
