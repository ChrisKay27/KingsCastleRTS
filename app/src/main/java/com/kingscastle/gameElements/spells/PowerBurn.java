package com.kingscastle.gameElements.spells;


import android.support.annotation.NonNull;

import com.kingscastle.effects.EffectsManager;
import com.kingscastle.effects.animations.PowerBurnAnim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.Bonuses;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public class PowerBurn extends InstantSpell{

	private final int armorBonus=10;


	private static Image iconImage;

	{
		setAliveTime(1000);
		setRefreshEvery(300);
	}


    @Override
	protected void refresh()
	{
		doDamage(cd.checkMultiHit( getTeamName() , getArea() ));
	}



	@Override
	public boolean cast( @NonNull MM mm )
	{
		super.cast(mm);

        if( !getTarget().getActiveAbilities().containsInstanceOf(this) ) {
            getTarget().getActiveAbilities().add(this);

            setStartTime(GameTime.getTime());
            setAliveTime(30000);

            loadAnimation();
            mm.getEm().add(getAnim(), EffectsManager.Position.InFront);

            return true;
        }
		else
			die();

		return false;
	}



    @Override
    public void doAbility() {
        Bonuses b = getTarget().getLQ().getBonuses();
        b.setArmorBonus(b.getArmorBonus()+armorBonus);
    }

	@Override
    public void undoAbility(){
		Bonuses b = getTarget().getLQ().getBonuses();
		b.setArmorBonus(b.getArmorBonus()-armorBonus);
	}


	@Override
	public int calculateDamage()
	{
		if( getCaster() != null )
			return 40 + getCaster().getLQ().getLevel() * 5;

		return 50;
	}



	@Override
	public int calculateManaCost( @NotNull @NonNull LivingThing aWizard)
	{
		if( aWizard != null )
			return 40 + aWizard.getLQ().getLevel() * 5;
		return 45;
	}



	@Override
	public void loadAnimation()
	{
		if( getAnim() == null )
		{
			setAnim(new PowerBurnAnim(getCaster().loc));
			getAnim().setLooping(false);
		}
	}




	@NonNull
    @Override
	public String toString() {
		return "Power Burn Armor";
	}

	@NonNull
    @Override
	public String getName() {
		return "PowerBurnArmor";
	}




	@NonNull
    @Override
	public Spell newInstance()	{
		return new PowerBurn();
	}




	@Override
	public Image getIconImage()
	{

		if( iconImage == null )
		{
			throw new IllegalStateException("There is no image for this animation.");
		}

		return iconImage;
	}






    @NonNull
    @Override
    public Abilities getAbility()				 {				return Abilities.POWERBURN ; 			}





}
