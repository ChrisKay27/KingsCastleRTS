package com.kingscastle.gameElements.spells;


import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.GroundSmasherAnim;
import com.kingscastle.framework.GameTime;
import com.kingscastle.framework.Image;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;

public class GroundSmasher extends InstantSpell
{

	private static Image iconImage;


	{
		setRefreshEvery(100);
	}

	public GroundSmasher(@NonNull LivingThing caster) {
		setCaster(caster);
	}


	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.GROUNDSMASHER ; 			}



	private int count = 0;
	@Override
	public void refresh()
	{
		doDamage(cd.checkMultiHit(getTeamName(), getArea()));
		++count;

		if( count >= 2 )
			die();
	}




	@Override
	public boolean cast( @NonNull MM mm )
	{
		super.cast(mm);
		setLoc(getCaster().loc);
		setStartTime( GameTime.getTime() );
		loadAnimation();
		mm.getEm().add(getAnim());

		return true;
	}




	@Override
	public int calculateDamage()
	{
		if(getCaster() != null )
		{
			return 60 + getCaster().getLQ().getLevel() * 3;
		}
		else
		{
			return 30;
		}
	}



	@Override
	public int calculateManaCost( @NotNull @NonNull LivingThing aWizard)
	{
		return 30 + aWizard.getLQ().getLevel() * 5 ;
	}

	@NonNull
    @Override
	public Spell newInstance() {
		return new GroundSmasher(getCaster());
	}


	@NonNull
    @Override
	public String getName()
	{
		return "GroundSmasher";
	}


	@Override
	public boolean hitsOnlyOneThing()
	{
		return false;
	}





	@NonNull
    @Override
	public String toString()
	{
		return "Ground Smasher";
	}




	@Override
	public void loadAnimation()
	{
		setAnim( new GroundSmasherAnim( loc ) );

	}






	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.quake_icon);
		}
		return iconImage;
	}






}
