package com.kingscastle.gameElements.livingThings.abilities;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.Anim;
import com.kingscastle.effects.animations.FiveRingsAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.Bonuses;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;

public class StrongArmorBuff extends Buff
{
	private final static String name = "ArmorBuff";

	private final float armorBonus = 80f;
	private static Image iconImage;
	{
		setAliveTime( 2000 );
		setRefreshEvery( 1000 );
	}

	public StrongArmorBuff(@NotNull LivingThing caster, @NotNull LivingThing target) {
		super(caster, target);
	}

	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.ARMOR_BUFF ; 			}

	@Override
	public void doAbility()
	{
		Bonuses b = getTarget().getLQ().getBonuses();
		b.setArmorBonus( b.getArmorBonus() + armorBonus );
	}

	@Override
	public void undoAbility()
	{
		Bonuses b = getTarget().getLQ().getBonuses();
		b.setArmorBonus(b.getArmorBonus() - armorBonus);
	}

	@Override
	protected void refresh(  EffectsManager em )
	{
		getAnim().reset();
	}

	@Override
	public int calculateManaCost(@NotNull @NonNull LivingThing aWizard)
	{
		return 0;
	}



	@Override
	public void loadAnimation( @NonNull MM mm )
	{
		if( getAnim() == null )
		{
			setAnim( new FiveRingsAnim( getTarget().loc ));
			getAnim().setOffs(new vector( Rpg.twoDp , -getTarget().area.height()/4 ));
		}
	}

	@Override
	protected void addAnimationToManager( @NonNull MM mm , @NonNull Anim anim2)
	{
		mm.getEm().add( anim2 , EffectsManager.Position.InFront );
	}

    @Override
    public boolean isOver() {
        return isDead();
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
		return new StrongArmorBuff(getCaster(),target);
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

	public float getArmorBonus() {
		return armorBonus;
	}








}
