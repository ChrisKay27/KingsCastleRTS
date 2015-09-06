package com.kingscastle.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.animations.RisingHeartsAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.Bonuses;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public class EverythingBuff extends Buff
{
	private final static String name = "EverythingBuff";
	private static Image iconImage;

	private final float everythingBonus = 1.15f;
	private boolean hasBuffed = false;

	{
		setAliveTime( 16000 );
	}

	public EverythingBuff(@NotNull LivingThing caster, @NotNull LivingThing target) {
		super(caster, target);
	}

    @Override
    public boolean isOver() {
        return isDead();
    }

    @NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.EVERYTHING_BUFF ; 			}


	@Override
	public void doAbility()
	{
		if( !hasBuffed )
		{
			Bonuses b = getTarget().getLQ().getBonuses();
			b.setDamageBonus ( b.getDamageBonus() * everythingBonus );
			b.addToSpeedBonusMultiplier(everythingBonus);
			b.setArmorBonus  ( b.getArmorBonus()  * everythingBonus );
			b.setHpRegenBonus( b.getHpRegenBonus() + 1 );
			b.setROABonus    ( (int) (b.getROABonus() * everythingBonus) );

			hasBuffed = true;
		}
	}

	@Override
	public void undoAbility()
	{
		if( hasBuffed )
		{
			Bonuses b = getTarget().getLQ().getBonuses();
			b.setDamageBonus( b.getDamageBonus()/everythingBonus );
			b.subtractFromSpeedBonusMultiplier(everythingBonus);
			b.setArmorBonus  ( b.getArmorBonus()/everythingBonus );
			b.setHpRegenBonus( b.getHpRegenBonus() -1 );
			b.setROABonus    ( (int) (b.getROABonus()/everythingBonus) );
			hasBuffed = false;
		}
	}



	@Override
	public int calculateManaCost(@NotNull @NonNull LivingThing aWizard)
	{
		return 0;
	}



	@Override
	public void loadAnimation( @NonNull MM mm )
	{
		if( getAnim() == null )		{
			setAnim( new RisingHeartsAnim( getTarget().loc ));
			getAnim().setLooping( false );
		}
	}



	@NonNull
    @Override
	public String toString() {
		return name;
	}
	@NonNull
    public String getName() {
		return name;
	}


	@NonNull
    @Override
	public Ability newInstance(@NotNull LivingThing target)	{
		return new EverythingBuff(getCaster(),target);
	}



	@Nullable
    @Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.multishot_icon);
		}
		return null;
	}


	public int getBonusPercent()
	{
		return (int) ((everythingBonus-1)*100);
	}









}
