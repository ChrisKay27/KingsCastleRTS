package com.kingscastle.gameElements.spells;

import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.kingscastle.effects.animations.ExplosionAnim;
import com.kingscastle.framework.Image;
import com.kingscastle.framework.Rpg;
import com.kingscastle.gameElements.livingThings.LivingThing;
import com.kingscastle.gameElements.managment.MM;
import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;


public class Explosion extends InstantSpell{

	private static Image iconImage;


	@NonNull
    private static RectF staticPerceivedArea = new RectF(-Rpg.getDp()*30,-Rpg.getDp()*30,Rpg.getDp()*30,Rpg.getDp()*30);


	@NonNull
    @Override
	public Abilities getAbility()				 {				return Abilities.EXPLOSION ; 			}

	@Override
	public void refresh()
	{
		doDamage(cd.checkMultiHit(getTeamName(), getArea()));

	}


	@Override
	public int calculateDamage()
	{
		if(getCaster() != null )
		{
			return 20 + getCaster().getLQ().getLevel() * 4;
		}
		return 7;
	}


	@Override
	public int calculateManaCost( @NotNull @NonNull LivingThing aWizard)
	{
		if(aWizard != null )
		{
			return 25 + aWizard.getLQ().getLevel() * 7;
		}
		return 1;
	}




	@Override
	public boolean cast( @NonNull MM mm )
	{
		super.cast(mm);
		hitCreatures( checkHit( getTeamName() ,this ) );
		loadAnimation();
		mm.getEm().add( getAnim() , true );

		return true;
	}


	@Override
	public void setLoc( @NonNull vector loc){
		loc.translate(0,-10 * Rpg.getDp());
		super.setLoc(loc);
	}


	@Override
	public void loadAnimation() {
		setAnim(new ExplosionAnim(loc));
	}




	@NonNull
    @Override
	public RectF getPerceivedArea()
	{
		if( staticPerceivedArea == null )
		{
			int sizeDiv2 = (int) (30 * Rpg.getDp()) ;
			staticPerceivedArea = new RectF( -sizeDiv2 , -sizeDiv2 , sizeDiv2 , sizeDiv2 );
		}
		return staticPerceivedArea;
	}



	@Override
	public boolean hitsOnlyOneThing() {
		return false;
	}




	@NonNull
    @Override
	public String getName() {
		return "Explosion";
	}





	@NonNull
    @Override
	public Spell newInstance()
	{
		return new Explosion();
	}


	@Override
	public Image getIconImage()
	{
		if( iconImage == null )
		{
			//	iconImage = Assets.loadImage(R.drawable.explosion_icon);
		}
		return iconImage;
	}

}
