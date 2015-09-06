package com.kingscastle.gameElements.spells;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.QuakeAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;

import org.jetbrains.annotations.NotNull;


public class Quake extends InstantSpell{


	private static Image iconImage;

	private static RectF staticPerceivedArea;

	public Quake() {

	}
	public Quake(@NonNull Quake quake_) {
		setDamage(quake_.getDamage());
	}



	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.QUAKE ; 			}

	@Override
	public int calculateDamage()
	{
		int damage = getDamage();
		if( getCaster() != null )
			return (int) ((int) (damage*0.75 + damage*0.5f*Math.random())*getCaster().getBonuses().getDamageBonus());
		else
			return damage;
	}

	@Override
	public int calculateManaCost( @NotNull @NonNull LivingThing aWizard)
	{
		return 20 + aWizard.getLQ().getLevel() * 3;
	}


	@Override
	public boolean cast( @NonNull MM mm )
	{
		super.cast(mm);
		doDamage(cd.checkMultiHit( getTeamName() , getArea() ) );

		die();

		loadAnimation();
		mm.getEm().add( getAnim() , true );
		return isDead();
	}







	@Override
	public RectF getStaticPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			float sizeDiv2 = Rpg.getDp()*30;
			staticPerceivedArea = new RectF(-sizeDiv2,-sizeDiv2,sizeDiv2,sizeDiv2);
		}
		return staticPerceivedArea;
	}

	@Override
	public void setStaticPerceivedArea(RectF staticPercArea) {
		staticPerceivedArea = staticPercArea;
	}

	@Override
	public RectF getPerceivedArea()
	{
		return getStaticPerceivedArea();
	}


	@Override
	public boolean hitsOnlyOneThing()
	{
		return false;
	}



	@NonNull
    @Override
	public String getName()
	{
		return "Quake";
	}




	@Override
	public void uncast()
	{
	}


	@Override
	public void loadAnimation()
	{
		setAnim( new QuakeAnim( loc ) );
	}




	@NonNull
    @Override
	public Spell newInstance()
	{
		return new Quake(this);
	}




	@Override
	public Image getIconImage()
	{

		if( iconImage == null )
		{
			//iconImage = Assets.loadImage(R.drawable.quake_icon);
		}

		return iconImage;
	}



}
